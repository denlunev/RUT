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

package com.mozgoweb.rut.gui;

import com.mozgoweb.rut.LauncherCore;
import java.util.Observable;
import com.mozgoweb.rut.LauncherCore.LauncherStatus;
import com.mozgoweb.rut.Messages;

public class LauncherConsole implements ILauncherGUI {

    private int prevPercent = 0;

    public LauncherConsole() {
        System.out.println(Messages.CHECKING_NEW_VERSION);
    }

    /**
     *
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        LauncherCore core = (LauncherCore) o;
       
        //Downloading
        if (core.getStatus() == LauncherStatus.DOWNLOADING) {
            
            //Print only percents, which multiple 10
            if(((int) core.getProgress()) % 10 == 0){
                //Check if percent value is changed
                //Otherwise it will print 10,10,10...
                if(prevPercent != (int)core.getProgress()){
                    prevPercent = (int)core.getProgress();
                    System.out.println(prevPercent + "%");
                }
            }
        //Downloading complete or current verision is up to date
        } else if (core.getStatus() == LauncherStatus.COMPLETE ||
                core.getStatus() == LauncherStatus.UP_TO_DATE) {

            if (core.getStatus() == LauncherStatus.UP_TO_DATE) {
                System.out.println(Messages.UP_TO_DATE);
            }else{
                System.out.println(Messages.COMPLETE);
            }

            //Run command from settings
            core.runCMD();
            //Close application
            System.exit(0);

        } else if (core.getStatus() == LauncherStatus.ERROR) {
            
            //Print error
            System.out.println(core.getError());
            //Run command anyway
            core.runCMD();
        }
    }
}
