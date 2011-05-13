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
import java.awt.Dimension;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import com.mozgoweb.rut.LauncherCore.LauncherStatus;
import com.mozgoweb.rut.Messages;

public class LauncherSwing extends JFrame implements ILauncherGUI {

    JLabel statusLabel = new JLabel();
    JProgressBar progressBar;

    public LauncherSwing() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(Messages.TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 100));
        frame.setPreferredSize(new Dimension(400, 100));
        frame.setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        //contentPane.setLayout(new BoxLayout(frame, BoxLayout.X_AXIS));

        statusLabel.setText(Messages.CHECKING_NEW_VERSION);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setEnabled(false);

        contentPane.add(statusLabel, null);
        contentPane.add(progressBar, null);
        frame.setContentPane(contentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        //launchProcess();
    }

    /**
     * 
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        LauncherCore l = (LauncherCore) o;
        String text = Messages.CHECKING_NEW_VERSION;
        
        if (l.getStatus() == LauncherStatus.DOWNLOADING) {
            progressBar.setEnabled(true);
            progressBar.setValue((int) l.getProgress());
            text = Messages.DOWNLOADING;
        } else if (l.getStatus() == LauncherStatus.COMPLETE ||
                l.getStatus() == LauncherStatus.UP_TO_DATE) {

            l.runCMD();
            System.exit(0);

        } else if (l.getStatus() == LauncherStatus.ERROR) {
            text = l.getError();
            l.runCMD();
        }

        statusLabel.setText(text);
    }
}
