# E-learning-Platform

<br /><br />
# Second Stage <br />
For the second stage I changed a bit the project structure by adding two new necessar classes for the app usage: Grade and Course. I also changed the layout of the project, abandoning the structuring into user and material subpackages.<br /><br />

## Structure <br />

### Entities <br />
&emsp;&emsp;&emsp;-> **Discipline** (Enum) <br />
&emsp;&emsp;&emsp;-> **User** (sealed abstract class) <br />
&emsp;&emsp;&emsp;-> **Student** (non-sealed ... extends User --> adds List<Material> materialList, List<Grade> gradeList, Double averageGrade) <br />
&emsp;&emsp;&emsp;-> **Teacher** (non-sealed ... extends User --> adds private String degree, private List<Course> teachCourses) <br />
&emsp;&emsp;&emsp;-> **DocumentType** (Enum) <br />
&emsp;&emsp;&emsp;-> **TestType** (Enum) <br />
&emsp;&emsp;&emsp;-> **Material** (abstract class) <br />
&emsp;&emsp;&emsp;-> **Document** (extends Material --> adds DocumentType documentType) <br />
&emsp;&emsp;&emsp;-> **Video** (extends Material --> adds LocalTime duration) <br />
&emsp;&emsp;&emsp;-> **Test** (extends Material --> adds TestType testType) <br />
&emsp;&emsp;&emsp;-> **Grade**  <br />
&emsp;&emsp;&emsp;-> **Course**  <br />
&emsp;&emsp;&emsp;-> **EssentialBook**  <br />
<br />
  
### Database <br />
For this project I used **Docker** to run my database in a container with a **PostgreSQL** server. After I created my local database using IntelliJ IDEA, I added the **config package**, which contains the **DatabaseConfiguration** class which is used to establish the database connection inside the application. The method that was added in order to close the established connection is never used, because everywhere I opened the database connection I used the **try-with-resources** statement so the connection would close itself. Also, to assure that the container with the database would not need to be constantly reopened I added the **docker-compose.yml** configuration file.<br /><br />
  ![PAO_ERD](https://github.com/stefaniarincu/E-learning-Platform/assets/93484228/40214220-43a8-42bd-853f-9ef23d1e010e)
<br /><br />
  
### Design patterns <br />
&emsp;&emsp;-> Creational design pattern: from this category I used the **Singleton design pattern**. Classes like the Menu, the mappers (CourseMapper, UserMapper etc), CsvWriter are implemented having a private constructor and an instance, which does not permit other classes to instantiate them. <br />
&emsp;&emsp;-> Behavioural design pattern: from this category I used the **Strategy design pattern**. The Student class have the "averageGrade" field, which can be calculated in two different methods. I assumed that I can determine the average grade by just doing the average of all the grades from that student list or by adding a weigth to each grade. The Student class requires a field that gets a variable from the **CalculateAverageGradeStrategy interface**, which by default implements the calculation of the average grade without ponders. This field can be changed using its specific Setter, and so the strategy of the average grade determination is changed. <br />
&emsp;&emsp;-> Structural design pattern: none yet <br />
<br /><br />
  
### Exceptions <br />
For the exception section I chose to define the seled exception ObjectNotFoundException which permits all of my other user-defined exceptions that reffers to objects not being found inside the database. Each of the other exceptions are prefixed with the object type name. <br />    
<br /><br />
  
### Generics <br />
Both service and repository package contains an interface (RepositoryGeneric & ServiceGeneric) which defines all of the basic methods needed for all of the entities. That interface is extended by the other repository/service interfaces. The abstract classes which are inherited, are also generics, because they accept objects that extends them.<br />    
<br /><br />

### Jackson Library and HttpClient <br />
For that I added the gateway package where I defined the Requests class, I added the EssentialBook class and also EssentialBookMapper. I assumed that I want to retrieve informations about university books that can be used as essentials inside an E-Learning Platform. The EssentialBookMapper uses the Jackson Library to map the JSON retrieved from the api into EssentialBook objects. Inside the Requests class I use HttpClient to connect to the api and get the data from there.<br />    
<br /><br />  
  
### CSV <br />
To write some messages inside CSV files I added the csv and utils package. There I added the CsvWriter and CsvLogger classes. 
In the [audit.csv]([https://github.com/stefaniarincu/E-learning-Platform/blob/tema2/audit.csv]) file I wrote some details about some basic database actions, along with the timestamp and importance of the message. For that I defined a LoggerRecord record to keep the messages that I obtained when performing database actions. In the repositories I sent the messages to the LogServiceImpl, so they can be written in the CSV file. The CsvLogger class is used for that.<br />
In the [essential_books.csv]([https://github.com/stefaniarincu/E-learning-Platform/blob/tema2/audit.csv](https://github.com/stefaniarincu/E-learning-Platform/blob/tema2/essential_books.csv]) file I wrote the data retrieved using the Requests class.<br />    
<br /><br />
  
### Logger <br />
I used Logger to log the errors that I retrieve from SQLExceptions and other exceptions that I got.<br />   
  ![ErrorLogger](https://github.com/stefaniarincu/E-learning-Platform/assets/93484228/c8a63b58-0eda-4df1-b3aa-0790f87fe139)
<br /><br />
  
### Threads & Runnable <br />
Just for academical purpose I added the threads package where I added the StudentPrinterThread class which implements Runnable and it's used to print the details about a givel list of students. In the Menu I use a method "demoOnThreads" where I print students with a higher or lower grade than a given value, using threads. Because I chose to implement the Runnable interface, each time that I create an object of StudentPrinterThread I can not just run that, and I need to create a Thread and start it and the run it.<br />   
<br /><br />
  
  
# First Stage (branch tema1) <br />
In this project I tried to implement an E-learning Platform. <br /> <br />
For the start, I assumed that on this platform we can have Users, that are either Students or Teachers. The difference between those two types of users is that the students have a list of materials that they have accesed, and a data structure that mantains the disciplines where they have at least one grade, along with the list of grades for that discipline. Also, the students have an average grade that is calculated depending on the values in the structure that mantains the grades (like a mini catalogue). Meanwhile, the teachers have a list of disciplines that they teach (upload materials). <br /> <br />
On this platform, the teachers are supposed to upload materials for the students, so they can learn. A Material can be a Document or a Test or a Video, so like a definition is a resource that gives a new piece of information or evaluates the previous knowledge of a student. A Document represents a written file, which can contain a course (like a lesson), some exercises (for practice, like a homework) or some solutions/implementations of the given problems. A Test is the material that contribuites to the evaluation of the student, and there can be EXAMS or QUIZEZ (they have a different level of dificulty). A Video is used to help with the visual representation of some abstract concepts. <br /><br />

## Structure <br />

### Classes <br />
&emsp;&emsp;&emsp;-> **Discipline** (Enum) <br />
&emsp;&emsp;&emsp;-> **User** (Abstract class) <br />
&emsp;&emsp;&emsp;-> **Student** (extends User --> adds List<Material> materials, TreeMap<Discipline, List<Double>> grades, Double averageGrade;) <br />
&emsp;&emsp;&emsp;-> **Teacher** (extends User --> adds List<Dicipline> discipline) <br />
&emsp;&emsp;&emsp;-> **DocumentType** (Enum) <br />
&emsp;&emsp;&emsp;-> **TestType** (Enum) <br />
&emsp;&emsp;&emsp;-> **Material** (Abstract class) <br />
&emsp;&emsp;&emsp;-> **Document** (extends Material --> adds DocumentType documentType) <br />
&emsp;&emsp;&emsp;-> **Video** (extends Material --> adds LocalTime duration) <br />
&emsp;&emsp;&emsp;-> **Test** (extends Material --> adds TestType testType, Double score) <br />
&emsp;&emsp;&emsp;-> **Statistic** (record that stores some statistics about the tests) <br />
<br />
### Services <br />
&emsp;&emsp;&emsp;-> **UserService** and **UserServiceImpl** <br />
&emsp;&emsp;&emsp;-> **StudentService** and **StudentServiceImpl** <br />
&emsp;&emsp;&emsp;-> **TeacherService** and **TeacherServiceImpl** <br />
&emsp;&emsp;&emsp;-> **MaterialService** and **MaterialServiceImpl** <br />
&emsp;&emsp;&emsp;-> **DocumentService** and **DocumentServiceImpl** <br />
&emsp;&emsp;&emsp;-> **TestService** and **TestServiceImpl** <br />
&emsp;&emsp;&emsp;-> **VideoService** and **VideoServiceImpl** <br />
<br />
### Functionalities <br />
&emsp;&emsp;&emsp;-> We can add/remove/update a user (both student or teacher) <br />
&emsp;&emsp;&emsp;-> We can add/remove/update a material (both document, test or video) <br />
&emsp;&emsp;&emsp;-> We can get all the documents that belongs to a certain discipline <br />
&emsp;&emsp;&emsp;-> We can get all the documents that belongs to a certain type of document<br />
&emsp;&emsp;&emsp;-> We can get all the videos that belongs to a certain discipline <br />
&emsp;&emsp;&emsp;-> We can get all the videos that have a duration lesser than a given time <br />
&emsp;&emsp;&emsp;-> We can get all the tests that belongs to a certain discipline <br />  
&emsp;&emsp;&emsp;-> We can get all the tests that belongs to a certain type of test <br />
&emsp;&emsp;&emsp;-> We can get all the students that have materials form a certain discipline <br />
&emsp;&emsp;&emsp;-> We can get all the students that have an average grade higher or lower than a specified grade sorted by a different criteria<br />  
&emsp;&emsp;&emsp;-> We can get all the teachers that uploaded materials from a given discipline <br />
&emsp;&emsp;&emsp;-> We can get all the users in the app sorted by their names <br /> 
&emsp;&emsp;&emsp;-> We can get a list of all the users in the app or all the materials inserted <br />
  
  


