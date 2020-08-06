# phonebook-m9-2
The project of morning 9-2 group

Here should lie all the steps to run the project on a local machine.

1. Connecting the project to the Database
The project creates phonebooks with contacts for different users.
For keeping data the project uses PostgreSQL Database.
At first, you need to download PostgreSQL here 
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads and install it on a local machine.
For installation PostgreSQL please follow steps on this page https://www.postgresqltutorial.com/install-postgresql/.
Second, you need to connect to PostgreSQL Database Server. All steps you can see here 
https://www.postgresqltutorial.com/connect-to-postgresql-database/.
Third, you need to create PostgreSQL Database follow the instruction 
https://www.postgresqltutorial.com/postgresql-create-database/.

After, you need to configure application.properties_ with the database name, PostgreSQL username and password.
https://www.jetbrains.com/help/idea/connecting-to-a-database.html#connect-to-postgresql-database

2. Adding Flyway
Flyway is an open-source database migration tool.
To create the first migration go to the application.properties and set:
spring.jpa.hibernate.ddl-auto=

Flyway automatically discovers migrations on the filesystem and on the Java classpath.
To keep track of which migrations have already been applied when and by whom, Flyway adds a schema history table to your schema in PostgresSQL.
To learn more about migrations go to https://flywaydb.org/documentation/migrations

3. For Intellij IDEA users:
To issue a query to a database, you must create a data source connection. 

a. In the Database tool window (View | Tool Windows | Database), click the Data Source Properties icon The Data Source Properties icon.
b. In the Data Sources and Drivers dialog, click the Add icon (+) and select PostgreSQL.
c. Specify database connection details: your PostgresSQL user name and password. 
d. To ensure that the connection to the data source is successful, click Test Connection.

4. Using Swagger
Swagger UI allows anyone to visualize and interact with the API’s resources without having any of the implementation logic in place. 
It’s automatically generated from your OpenAPI (formerly known as Swagger) Specification, 
with the visual documentation making it easy for back end implementation and client side consumption.

To use Swagger Ui run the PhonebookApiApplication and visit the http://localhost:8080/swagger-ui.html page in your browser
