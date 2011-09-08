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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

public class FileUtils {

    /**
     * 
     * @param file
     * @return
     */
    public static String getContents(File file) {
       
        StringBuilder contents = new StringBuilder();

        try {
            // If file doesn't exist, create it
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                file.createNewFile();
            }

            // Use buffering, reading one line at a time
            // FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(file));
            try {
                String line = null; // not declared within while loop
		
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }

    /**
     * Change the contents of text file in its entirety, overwriting any
     * existing text.
     *
     * This style of implementation throws all exceptions to the caller.
     *
     * @param file
     *            is an existing file which can be written to.
     * @param aContents
     * @throws IllegalArgumentException
     *             if param does not comply.
     * @throws FileNotFoundException
     *             if the file does not exist.
     * @throws IOException
     *             if problem encountered during write.
     */
    public static void setContents(File file, String aContents)
            throws FileNotFoundException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + file);
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Should not be a directory: "
                    + file);
        }
        if (!file.canWrite()) {
            throw new IllegalArgumentException("File cannot be written: "
                    + file);
        }

        // use buffering
        Writer output = new BufferedWriter(new FileWriter(file));
        try {
            // FileWriter always assumes default encoding is OK!
            output.write(aContents);
        } finally {
            output.close();
        }
    }

    /**
     * 
     * @param url
     * @return
     */
    public static String getFileName(String path) {
        String filename = path;
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    public static String getCurrentDir(){
        return new File(".").getAbsolutePath();
    }
}
