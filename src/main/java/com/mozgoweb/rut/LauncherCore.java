/**
 * Remote Update Tool.
 *
 * Copyright (C) 2010, Denis Lunev <den.lunev@gmail.com>
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * More projects on http://www.mozgoweb.com
 */

package com.mozgoweb.rut;

import com.mozgoweb.rut.utils.FileUtils;
import com.mozgoweb.rut.utils.SettingsReader;
import com.mozgoweb.rut.utils.VersionManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

public class LauncherCore extends Observable implements Runnable {

    private String urlFile = SettingsReader.getInstance().getRemoteFile();
    private String runCommand = SettingsReader.getInstance().getRunCommand();
    private String error = "";
    private int fileSize = 0;
    private int downloadedSize = 0;
    private final int MAX_BUFFER_SIZE = 1024;
    private LauncherStatus status;
    private VersionManager versionManager;

    public enum LauncherStatus {

        UP_TO_DATE, //Remote version the same as current
        DOWNLOADING, //Downloading is in progress
        COMPLETE, //Downloading is finished
        ERROR //Downloading failed
    }

    public LauncherCore() {
        versionManager = new VersionManager();
    }

    public void download() {
        try {
            //Check, if new version is available
            if (versionManager.isNewVerAvailable()) {
                //Update is available, start to download
                //Create new thread and invoke this.run()
                Thread thread = new Thread(this);
                thread.start();
            } else {
                //Remote version the same as current
                status = LauncherStatus.UP_TO_DATE;
                stateChanged();
            }
        } catch (FileNotFoundException ex) {
            //Cannot retrieve remote version
            status = LauncherStatus.ERROR;
            error(Messages.ERR_CANNOT_REMOTE_VERSION);
        }
    }

    public void run() {

        HttpURLConnection connection = null;
        RandomAccessFile file = null;
        InputStream stream = null;
        status = LauncherStatus.DOWNLOADING;

        try {
            //Connect to the update server
            URL url = new URL(urlFile);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() / 100 != 2) {
                System.out.println("Connection");
                //throw new ConnectException();
                //error(Messages.ERR_SERVER_NOT_RESPONDED);
            }

            fileSize = connection.getContentLength();

            stream = connection.getInputStream();
            //Open file to saving
            file = new RandomAccessFile(FileUtils.getFileName(url), "rw");
            //Find begin of the file
            file.seek(0);

            while (status == LauncherStatus.DOWNLOADING) {

                byte buffer[];
                if (fileSize - downloadedSize > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[fileSize - downloadedSize];
                }

                int read = stream.read(buffer);

                if (read == -1) {
                    // File already downloaded
                    break;
                }

                file.write(buffer, 0, read);
                downloadedSize += read;
                stateChanged();
            }

            //file.close();

            //Save new version to settings file
            versionManager.saveNewVersion();

            //Change status to completed
            if (status == LauncherStatus.DOWNLOADING) {
                status = LauncherStatus.COMPLETE;
                stateChanged();
            }

        } catch (MalformedURLException e) {
            error(String.format(Messages.ERR_UPDATE_FILE_NOT_FOUND, urlFile));
            //e.printStackTrace();
        } catch (FileNotFoundException e) {
            error(String.format(Messages.ERR_UPDATE_FILE_NOT_FOUND, urlFile));
            //e.printStackTrace();
        } catch (IOException e) {
            error("error3");
            e.printStackTrace();
        }catch (Throwable e){
            error(e.getMessage());
        } finally {

            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println("Update");
        //stateChanged();
    }

    public void runCMD() {
        //Run command after downloading update
        try {
            Runtime.getRuntime().exec(runCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param err
     */
    private void error(String err) {
        status = LauncherStatus.ERROR;
        error = err;
        stateChanged();
    }

    /**
     *
     * @return
     */
    public float getProgress() {
        return ((float) downloadedSize / fileSize) * 100;
    }

    /**
     *
     * @return
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     *
     * @return
     */
    public LauncherStatus getStatus() {
        return status;
    }

    private void stateChanged() {
        //Notify observers (invoke ILauncherGUI.update())
        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @return
     */
    public String getError() {
        return error;
    }
}
