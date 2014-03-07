ssdm-populate
=============

Java classes and program to populate a MySQL or SqlServer database with social security death master file information.
This program will load the publicly-available Social Security Death Master file data (all 100 million records, ultimately) into a suitably-schematized database.
It will also provide a limited amount of lookup capability, mainly for testing purposes.
The goal is to set up a database allowing matching of death records, from other genealogical research sources, to the Social Security database.

To build:<br>
Download the Standard Widget Toolkit and import the org.eclipse.swt project into Eclipse
(Here's a FAQ on configuring SWT into Eclipse: https://wiki.eclipse.org/FAQ_How_do_I_configure_an_Eclipse_Java_project_to_use_SWT%3F)
Download the JDBC connector for MySQL and install the compiled JAR into the lib/ folder.  Name the file mysql-connector-java-bin.jar.
Download the JDBC connector for SqlServer and install the Sqljdbc4.jar file into the lib/ folder.

To test:
Select "Can of beans" from the database type dropdown list (MySQL and SqlServer connections are still in development)
Click "Connect" to initialize the in-memory "database"
Enter any username / password combination and Click "OK"
Check the "Add items to database..." checkbox
Click the "Open" button and select the ssdm_sample.txt sample data file
After loading the data, enter a SSAN from the text output in the Console window in the "Query SSAN:" text box
Click "Look it up" - you should get a message box with the name of the person in it
Now enter your own SSAN into the text and click "Look it up"
You should be told that no matching record was found.  If, instead, your own name appears, check your pulse immediately.
