# sanger-lims

# Instructions

The application is written in Java. It will need to be downloaded and compiled in order to run. The method that should be run is called 'main' and is in the class also called 'main'. I followed the below steps to run the app:

1) Download Eclipse IDE for Java developers, version 2019-06 (4.12.0)
2) Open the project in Eclipse
3) Go to File > Export > Java > Runnable JAR file, click Next
4) Launch configuration: main - Labware & Containers LIMS
5) Call it LIMS_app
6) Library Handling: Extract required libraries into generated JAR
7) Click Finish
8) Open Windows Powershell in folder containing the file you just generated
9) Run java -jar LIMS_app.jar
10) The program should start and you should be prompted for user input

# Assumptions

1) Barcodes are unique across all tubes, not just within sample tubes and library tubes.
2) Samples can be re-tagged, with the new tag replacing the old one.
3) The user does not need to specify the order in which the samples are processed during the protocol (final user story).
