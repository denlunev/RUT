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

import com.mozgoweb.rut.gui.ILauncherGUI;
import com.mozgoweb.rut.gui.LauncherConsole;
import com.mozgoweb.rut.gui.LauncherSwing;

public class App {

    public static void main(String[] args) {

        ILauncherGUI launcher = null;

        if (App.isGUI(args)) {
            launcher = new LauncherSwing();
        } else {
            launcher = new LauncherConsole();
        }

        LauncherCore core = new LauncherCore();
        core.addObserver(launcher);
        core.download();

    }

    public static boolean isGUI(String[] args) {
        if (args.length > 0 && args[0].equals("-gui")) {
            return true;
        } else {
            return false;
        }
    }
}
