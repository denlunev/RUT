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

import com.mozgoweb.rut.utils.SettingsReader;
import java.util.ResourceBundle;

public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        String currentVersion = SettingsReader.getInstance().getCurrentVersion();
//        String remoteFile = SettingsReader.getInstance().getRemoteFile();
//        String remoteVersionFile = SettingsReader.getInstance().getRemoteVersionFile();
//        String RunFile = SettingsReader.getInstance().getRunCommand();
//
//        System.out.println("Current version: " + currentVersion);
//        System.out.println("remoteFile: " + remoteFile);
//        System.out.println("remoteVersionFile: " + remoteVersionFile);
//        System.out.println("CRunFile: " + RunFile );
//        System.out.println("Current version: " + currentVersion);
//
//        currentVersion = "changed-version-0.300000";
//        SettingsReader.getInstance().setCurrentVersion(currentVersion);
//        SettingsReader.getInstance().saveSettings();

        for (int i = 0; i < 100; i++) {
            if(i % 10 == 0){
                System.out.println(i);
            }

        }


    }

}
