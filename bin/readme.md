# Rating Base Backend

## Install
Import the project as a maven project. Then build using clean install.

## application.properties configuration
The configuration is set to the azure database as default. This database is slow but works for testing out the different features.

### Own configuration
Replace the url with your own. Currently only mysql and mssql works. It is currently very limited and only works with the prefix  
"jdbc:mysql://" and "jdbc:sqlserver://"  

    spring.datasource.url=jdbc:mysql://localhost:3306/reviews
    
Username for the database    
    
    spring.datasource.username=test
    
Password for the database    
    
    spring.datasource.password=1234
    
Hibernate dialect. This is not always needed. Some experimenting might be needed

    spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect

The driver is also not always needed. Check which driver is used by your database

    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

This is for the terminal only.

    spring.jpa.show-sql=true
    
The following may be changed depending on the size of the data you want to upload to your database.
    
    spring.servlet.multipart.max-file-size=100MB
    spring.servlet.multipart.max-request-size=100MB

There are more alternatives which can be found here. Do note that most of these are not tested and may yield unknown errors
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html