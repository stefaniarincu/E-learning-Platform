# E-learning-Platform

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

