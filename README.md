Remote Update Tool
==================================================

RUT has 2 modes: console and Swing gui.
To run console version, invoke following command in terminal:

    java -jar rut.jar


To run gui version:

    java -jar rut.jar -gui


Windows version (rut.exe) runs in gui mode by default.

Setting.xml has following options:

*   _current-version_: Current version of application on client computer, changes automatically after updating.
*   _remote-version-file_: Txt file, which contains last version of application (e.g. 0.2) on remote server.
*   _remote-file_: File on remote server, which should be downloaded in the updating process.
*   _run-command_: Console command, which will be invoked after updating, e.g. it can run the downloaded file. 

Example:

    <settings>
         <current-version>0.1</current-version>
         <remote-version-file>
              http://example.com/version.txt
         </remote-version-file>
         <remote-file>
              http://example.com/file.jar
         </remote-file>
         <run-command>
              java -jar file.jar
         </run-command>
    </settings>


http://example.com/version.txt contains "0.2" (without quotes).
After updating, current-version changes to 0.2 and file.jar runs.













