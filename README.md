###   Ticket Service - Problem and Solution


The intent of the problem domain is that the customer should be to view the seats available in a venue, hold them temporarily 
for a period of time, and finally reserve them if customer wishes so.


### Assumptions

1) 60 seconds duration for the seat hold is assumed.
2) Customer can not reserve a subset of the seats held.
3) TicketService methods are not exposed through ReST architectural style.
4) A persistence store is not used. A map based data structure to represent the problem domain is used.

### Technologies Used

1) Java 1.8 [Programming Language]
2) Gradle [Build]
3) JUnit and Spring-test [Testing]
4) CheckStyle, PMD, FindBugs [Code Quality]
5) Jacoco [Code Coverage]
6) Git [Version Control]
7) Spring Framework [Dependency Management]
8) lombok [Boilerplate Code Generation]
9) SLFJ [Logging]

### Running the Application

1) Make sure JDK 1.8 and Gradle are installed and configured on your path.
2) Checkout using Git or download the zip from TicketMaster repository to your choice of directory.
3) Go to the TicketMaster directory in the command-line and run the following build commands.
      
      ./gradlew build  
      
      This compiles,runs code quality checks, runs tests, generates code coverage reports.
      
       ./gradlew test
      
      This compiles, runs tests and generates jacoco code coverage reports.
      
      The generated reports can be found in build/reports directory.
 
 