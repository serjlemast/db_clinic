<h1>Info about web project</h1>

JEE (Servlet API - 4.0.1, JSTL, JSP)
<br>
JDK 8
<br>
JDBC
<br>
REST
<br>
JS/HTML/AJAX

<h2>Tomcat</h2>
link: https://tomcat.apache.org/tomcat-9.0-doc/index.html
<br>
version - 9.0.54
<br>
Include Tomcat to IDE and add path
<br>
Deployment section add artifact(projectName.war)

<h2>Docker</h2>
link: https://hub.docker.com/_/mysql
<br>
Environment Variables:
<br>
MYSQL_ROOT_PASSWORD = 12345
<br>
DEFAULT user name to database = root
<br>
Run command in terminal:
<br>
docker run -d -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=12345 -d mysql:tag 

<h2>Mysql  version 8.0.27</h2>
Data base contains two tables user and role
<br>
User table contains foreign key to role table,named role_id
<br>
To use mysql database run command:
<br>
mysql -p (connection to db)
<br>
SHOW SCHEMAS; - show all databases
<br>
USE [database name];
<br>
Dump database - https://kb.iu.edu/d/apnn
<br>
SQL script located in resource folder - Dump20220126.sql

<h2>IntelliJ IDEA</h2>
IDEA 2021 Ultimate
<br>
Plugins: Lombook
<br>

<h1>First start project</h1>
<ol>
<li>Check database(user:root,pas:12345)</li>
<li>Add image to docker(mysql)</li>
<li>Run docker container</li>
<li>Click on cli(on docker) - administration db</li>
<li>Enter command - mysql -p db_clinic</li>
<li>Password - 12345</li>
<li>Include Tomcat and add local server on port 8080</li>
<li>Create archetype</li>
<li>Run Tomcat</li>
</ol>