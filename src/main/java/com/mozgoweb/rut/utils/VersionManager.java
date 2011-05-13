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

package com.mozgoweb.rut.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class VersionManager {

    //private String localVersionFile = "";
    private String remoteVersionFile = "";
    private String currentVersion = "";
    private String remoteVersion = "";

    public VersionManager() {
        this.remoteVersionFile = SettingsReader.getInstance().getRemoteVersionFile();
    }

    /**
     *
     * @return
     */
    private String getCurrentVersion() {
        String version = "";
        version = SettingsReader.getInstance().getCurrentVersion();
        return version.trim();
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    private String getRemoteVersion() throws FileNotFoundException {
        String version = "";
        URLConnection urlConn = null;
        BufferedReader br = null;
        URL url = null;
        
        try {
            url = new URL(remoteVersionFile);
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);

            br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            version = br.readLine();

        } catch (Exception e) {
            throw new FileNotFoundException();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return version.trim();
    }

    public void saveNewVersion() {
        SettingsReader.getInstance().setCurrentVersion(remoteVersion);
        SettingsReader.getInstance().saveSettings();
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    public boolean isNewVerAvailable() throws FileNotFoundException {
       
        currentVersion = getCurrentVersion();
        remoteVersion = getRemoteVersion();

        System.out.println("current: " + currentVersion);
        System.out.println("remote: " + remoteVersion);

        if (currentVersion.equals(remoteVersion)) {
            return false;
        } else {
            return true;
        }
    }
}
