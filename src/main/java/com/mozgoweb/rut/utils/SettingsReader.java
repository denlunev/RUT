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

import com.mozgoweb.rut.Messages;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class SettingsReader {

    static private SettingsReader instance = null;
    private String currentVersion;
    private String remoteVersionFile = "";
    private String remoteService = "";
    private String remoteFile = "";
    private String remoteHostname = "";
    private String runCommand = "";
    private Element rootElement = null;
    private String configFileName = FileUtils.getCurrentDir() + "/settings.xml";
    private final String CURRENT_VERSION = "current-version";
    private final String REMOTE_VERSION_FILE = "remote-version-file";
    private final String REMOTE_FILE = "remote-file";
    private final String RUN_COMMAND = "run-command";

    /**
     *
     * @return
     */
    static public SettingsReader getInstance() {
        if (instance == null) {
            instance = new SettingsReader();
        }

        return instance;
    }

    private SettingsReader() {
        parseSettingsFile();
    }

    /**
     * This method splits a String URL to it's components.
     * http://www.google.com/folder/Filename.txt becomes
     * http;www.google.com;/folder/Filename.txt
     * @param url String URL
     * @return Array containing the components of the URL
     */
    private String[] splitStringUrl(String url) {
        String[] returnarray = new String[3];
        String[] split = url.split("://");
        returnarray[0] = split[0];
        url = url.substring(returnarray[0].length() + 3);
        split = url.split("/");
        returnarray[1] = split[0];
        returnarray[2] = url.substring(returnarray[1].length());
        return returnarray;
    }

    private void parseSettingsFile() {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document docXml = builder.build(new File(configFileName));
            rootElement = docXml.getRootElement();

            //Retrieve Settings
            currentVersion = rootElement.getChild(CURRENT_VERSION).getTextTrim();
            remoteVersionFile = rootElement.getChild(REMOTE_VERSION_FILE).getTextTrim();
            remoteFile = rootElement.getChild(REMOTE_FILE).getTextTrim();
            String[] split = splitStringUrl(remoteFile);
            remoteService = split[0];
            remoteHostname = split[1];
            remoteFile = split[2];
            runCommand = rootElement.getChild(RUN_COMMAND).getTextTrim();


        } catch (JDOMException ex1) {
            System.out.println(Messages.ERR_WRONG_SETTINGS + ": " + ex1.getMessage());
        } catch (Exception ex2) {
            System.out.println(Messages.ERR_CANNOT_READ_SETTINGS + ": " + ex2.getMessage());
        }
    }

    //Save setting into file
    //In this case, only current version
    public void saveSettings() {
        XMLOutputter outputter = new XMLOutputter();
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(configFileName));
            outputter.output(rootElement, output);
        } catch (IOException e) {

            System.out.println(Messages.ERR_CANNOT_SAVE_SETTINGS + ": " + e.getMessage());

        } finally {
            try {
                output.close();
            } catch (IOException ex) {
            }
        }

    }

    /**
     *
     * @return
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        //this.currentVersion.setText(currentVersion);
        rootElement.getChild(CURRENT_VERSION).setText(currentVersion);
        //rootElement.setContent(this.currentVersion);
    }

    /**
     *
     * @return
     */
    public String getRemoteFile() {
        return remoteFile;
    }

    /**
     *
     * @return
     */
    public String getRemoteVersionFile() {
        return remoteVersionFile;
    }

    /**
     * 
     * @return
     */
    public String getRunCommand() {
        return runCommand;
    }

    /**
     * This method returns the remote Hostname. (e.g www.google.com)
     * @return String representation of remote Hostname
     */
    public String getRemoteHostname() {
        return remoteHostname;
    }

    /**
     * This method returns the remote service (e.g http)
     * @return String representation of remote service
     */
    public String getRemoteService() {
        return remoteService;
    }
}
