# Getting Started
I used a .env file with vars on my local machine to hide the database credentials.
The .env file is not included in the project.
create a .env file in the root of the project with the following content:
DB_USERNAME=<username>
DB_PASSWORD=<password>

If you run it from intellij or eclipse, you need to set the env vars in the run configuration.
If you run it from the command line, you need to set the env vars in the command line:
mac and linux with: export $(grep -v '^#' .env | xargs)
windows with: for /f "usebackq tokens=1,2 delims==" %A in (.env) do set %A=%B
after that you can run the project with mvn ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev (after a clean install)
Docker-compose has a build-in support for the .env file.

run the docker-compose start -d under the folder src/main/docker
to start the database.

Then build teh project with mvn clean install
Then run the project with ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
Then open the browser and go to http://localhost:8080/api/v1/employee
/api/v1/employee/add (with an employeeModel object in the body)
/api/v1/employee/find/{email} (with an email as path variable)
/api/v1/employee/update (with an employeeModel object in the body)
/api/v1/employee/delete (with an employeeModel object in the body)

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/maven-plugin/build-image.html)
* [Spring Security](https://docs.spring.io/spring-boot/3.4.5/reference/web/spring-security.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

