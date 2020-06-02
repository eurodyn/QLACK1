Installation and Configuration :

(Please note that the attached "dbmonster.zip" folder contains all these below steps installed and configured. )


1. Get the latest "dbmonster-core" distribution from the attached zip file or download from the "http://dbmonster.kernelpanic.pl/downloads.html" and copy it in to 
	<Installed_dir>/dbmonster/dbmonster-core-1.0.3/dbmonster-core-1.0.3

2. Get the "dbmonster-ant.jar" file from  the attached zip file or download from the "http://dbmonster.kernelpanic.pl/downloads.html" and copy it in to 

	<Installed_dir>/dbmonster/dbmonster-ant

3. Create a config folder and Schema folder if not exist inside of  "<Installed_dir>/dbmonster/dbmonster-core-1.0.3/dbmonster-core-1.0.3" folder 

4. Create a "dbmonster.properties" file in config folder if not exist 
	
	in properties file place these properties for your db (these properties for Mysql db):

	dbmonster.jdbc.driver=com.mysql.jdbc.Driver
	dbmonster.jdbc.url=jdbc:mysql://localhost/qlack
	dbmonster.jdbc.username=root
	dbmonster.jdbc.password=root
	dbmonster.max-tries=1000
	dbmonster.rows=1000

5. Create a "qlack_schema.xml" file in Schema folder 

6. Write schema for all the tables  in Qlack DB in "qlack_schema.xml"  file. 

7. place mysql-connector-java-5.1.13.jar in dbmonster-core-1.0.3\dbmonster-core-1.0.3\lib folder if not exist 


8. Change the path of dbmonster.home ,dbmonster.ant,dbmonster.config and dbmonster.schemas in build.properties file 
	for example : dbmonster.home=dbmonster-core-1.0.3/dbmonster-core-1.0.3
			  dbmonster.ant=dbmonster-ant
			  dbmonster.config=dbmonster-core-1.0.3/dbmonster-core-1.0.3/config
			  dbmonster.schemas=dbmonster-core-1.0.3/dbmonster-core-1.0.3/schema

And after completing the above steps execute the ant target as below 

Build :

 open  command prompt and in  dbmonster path (where you unpacked zip file ) and type "ant gendb".
for ex : C:\dbmonster > ant gendb 

Once the schema executes succesfully it displays BUILD SUCCESSFUL.