---------------
DATABASE UPDATE
---------------

---------------
Usage
---------------
WINDOWS:
java -cp %QLACK_HOME%\Qlack-Extras\Qlack-Extras-DBRebuild\target\Qlack-Extras-DBRebuild-1.0.jar;<Database Java Connector path> 
com.eurodyn.qlack.extras.dbrebuild.App -scripts <comma separated scripts without spaces> 
-dbuser <username> -dbpassword <password> -dburl <connection URL of the database server> -dbdriver <fully classified name of the JDBC driver> -properties <Properties file>

UNIX:
java -cp $QLACK_HOME/Qlack-Extras/Qlack-Extras-DBRebuild/target/Qlack-Extras-DBRebuild-1.0.jar:<Database Java Connector path> 
com.eurodyn.qlack.extras.dbrebuild.App -scripts <comma separated scripts without spaces> 
-dbuser <username> -dbpassword <password> -dburl <connection URL of the database server> -dbdriver <fully classified name of the JDBC driver> -properties <Properties file>/


---------------
Example MySQL
---------------
java -cp %QLACK_HOME%\Qlack-Extras\Qlack-Extras-DBRebuild\target\Qlack-Extras-DBRebuild-1.0.jar;C:\JavaLibs\mysql-connector-java-5.1.21.jar 
com.eurodyn.qlack.extras.dbrebuild.App -scripts %QLACK_HOME%\\QlackFuse\\QlackFuse-Modules\\QlackFuse-Modules-Lexicon\\QlackFuse-Modules-Lexicon-ejb\\setup\\sql\\mysql\\qlack_fuse_lexicon 
-dbuser root -dbpassword root -dburl jdbc:mysql://localhost:3306/qlack -dbdriver com.mysql.jdbc.Driver


---------------
Example Oracle
---------------
java -cp %QLACK_HOME%\Qlack-Extras\Qlack-Extras-DBRebuild\target\Qlack-Extras-DBRebuild-1.0.jar;C:\JavaLibs\ojdbc6.jar 
com.eurodyn.qlack.extras.dbrebuild.App -scripts %QLACK_HOME%\\QlackFuse\\QlackFuse-Modules\\QlackFuse-Modules-Lexicon\\QlackFuse-Modules-Lexicon-ejb\\setup\\sql\\oracle\\qlack_fuse_lexicon 
-dbuser qlack -dbpassword qlack -dburl jdbc:oracle:thin:@//localhost:1521/qlack -dbdriver oracle.jdbc.OracleDriver -properties C:\temp\sql-replace.properties