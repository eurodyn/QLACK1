-----------------------------------------------------------------
------------------Sample usage (Properties2SQL)------------------
-----------------------------------------------------------------
MySQL:
java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\mysql-connector-java-5.1.21.jar com.eurodyn.qlack.extras.translationsconverter.Properties2SQL 
-props C:\translations\reference_data_translations_ -modifier qlack -dburl jdbc:mysql://localhost:3306/qlack -dbuser root -dbpassword root -dbdriver com.mysql.jdbc.Driver -charset UTF-8

java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\mysql-connector-java-5.1.21.jar com.eurodyn.qlack.extras.translationsconverter.Properties2SQL 
-props C:\translations\reference_data_translations_ -modifier qlack -dburl jdbc:mysql://localhost:3306/qlack -dbuser root -dbpassword jijikos -dbdriver com.mysql.jdbc.Driver 
-outputFile C:\translations\translations.sql -charset UTF-8

Oracle:
java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\ojdbc6.jar com.eurodyn.qlack.extras.translationsconverter.Properties2SQL 
-props C:\translations\reference_data_translations_ -modifier qlack -dburl jdbc:oracle:thin:@//localhost:1521/qlack -dbdriver oracle.jdbc.OracleDriver -dbuser qlack -dbpassword qlack -charset UTF-8 

java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\ojdbc6.jar com.eurodyn.qlack.extras.translationsconverter.Properties2SQL 
-props C:\translations\reference_data_translations_ -modifier qlack -dburl jdbc:oracle:thin:@//localhost:1521/qlack -dbdriver oracle.jdbc.OracleDriver -dbuser qlack -dbpassword qlack 
-outputFile C:\translations\translations.sql -charset UTF-8


-----------------------------------------------------------------
------------------Sample usage (Properties2XLS)------------------
-----------------------------------------------------------------
java -cp Qlack-Extras-TranslationsConverter-1.0.jar com.eurodyn.qlack.extras.translationsconverter.Properties2XLS 
-props C:\translations\reference_data_translations_ -outputFile C:\translations\reference_data_translations.xls -mainFile C:\translations\reference_data_translations_en.properties"


-----------------------------------------------------------------
------------------Sample usage (XLS2Properties)------------------
-----------------------------------------------------------------
java -cp Qlack-Extras-TranslationsConverter-1.0.jar; com.eurodyn.qlack.extras.translationsconverter.XLS2Properties 
-inputFile C:\translations\ui_translations.xls -outputPath C:\translations


-----------------------------------------------------------------
------------------Sample usage (XLS2SQL)------------------
-----------------------------------------------------------------
MySQL:
java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\mysql-connector-java-5.1.21.jar com.eurodyn.qlack.extras.translationsconverter.XLS2SQL 
-groupIDs 9e70c7fa-a366-42cb-906b-87e05e7078ca,b69892cc-c20a-4fb1-9344-c2570fcb412b -modifier qlack -dburl jdbc:mysql://localhost:3306/qlack -dbuser root -dbpassword root 
-dbdriver com.mysql.jdbc.Driver -inputFile C:\translations\ui_translations.xls -outputPath C:\translations -suffix v0001 -charset UTF-8

Oracle:
java -cp Qlack-Extras-TranslationsConverter-1.0.jar;C:\JavaLibs\ojdbc6.jar com.eurodyn.qlack.extras.translationsconverter.XLS2SQL 
-groupIDs 9e70c7fa-a366-42cb-906b-87e05e7078ca,b69892cc-c20a-4fb1-9344-c2570fcb412b -modifier qlack -dburl jdbc:oracle:thin:@//localhost:1521/qlack 
-dbdriver oracle.jdbc.OracleDriver -dbuser qlack -dbpassword qlack  -inputFile C:\translations\ui_translations.xls -outputPath C:\translations -suffix v0001 -charset UTF-8