Social Media Api

A social  media api that enable users to post, comment, follow users, unfollow users, like posts.

How to install this project: clone repository, open in intellij or visual studio code. This project is built on java(version 11), maven bild tool and springboot an extension of spring framework.


To use this project make sure you already have JDK installation on your system, (Either set the JAVA_HOME environment variable pointing to your JDK installation or have the java executable on your PATH), maven and springframework.


Setting up project in Maven:
We need to start by creating a Maven pom.xml file. The pom.xml is the recipe that is used to build your project. Open your favorite text editor and add the following:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>myproject</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.2</version>
    </parent>

    <!-- Additional lines to be added here... -->

</project>

The preceding listing should give you a working build. You can test it by running mvn package (for now, you can ignore the “jar will be empty - no content was marked for inclusion!” warning).



Most Spring Boot applications use the spring-boot-starter-parent in the parent section of the POM. The spring-boot-starter-parent is a special starter that provides useful Maven defaults. It also provides a dependency-management section so that you can omit version tags for “blessed” dependencies.





Since we are developing a web application, we add a spring-boot-starter-web dependency. Before that, we can look at what we currently have by running the following command:

$ mvn dependency:tree

[INFO] com.example:myproject:jar:0.0.1-SNAPSHOT


 mvn dependency:tree command prints a tree representation of your project dependencies. You can see that spring-boot-starter-parent provides no dependencies by itself. To add the necessary dependencies, edit your pom.xml and add the spring-boot-starter-web dependency immediately below the parent section:

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

If you run mvn dependency:tree again, you see that there are now a number of additional dependencies, including the Tomcat web server and Spring Boot itself.


All dependencies used are in the pom.xml file

You'll be getting the following when all installation and guide is well followed


![loading](https://github.com/Greatnex1/social-media-api/assets/72028378/37e17fea-a99f-4796-afda-940a04745da9)

regiser user:
![register](https://github.com/Greatnex1/social-media-api/assets/72028378/98494969-7b62-43b8-b10c-bc8f6e57a0f8)



I used maildev to serve as a platform for pseudo mails, so, a verification link and welcome message is sent to an individual who just registered.
![verified](https://github.com/Greatnex1/social-media-api/assets/72028378/93b6e65e-c695-46c3-b1ee-03c04cdbacac)


user login:
![login](https://github.com/Greatnex1/social-media-api/assets/72028378/22750642-34aa-4e08-8c7c-b2ecadfb8797)

comment on the post created:
![comment](https://github.com/Greatnex1/social-media-api/assets/72028378/d5a666da-6c5f-448c-ad4a-02e5e3af8781)


All configurations are stored in a yaml.file integrated with application.properties.

Test was carried out using mockito.



In the building of this project, i learnt how to write a READ_ME file and interpret error meesages.


MIT License
Copyright (c) [2023] [Nouah Akoni]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

