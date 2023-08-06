# social-media-api
a social app where users can post, comment, follow and unfollow other users and like post.

Social Media Api

A social  media api that enable users to post,comment,follow and unfollow other users, like post.

How to install this project: clone repository, open in intellij or visual studio code. This project is built on java(version 11), maven bild tool and springboot an extension of spring framework.


To use this project make su


re you already have JDK installation on your system, (Either set the JAVA_HOME environment variable pointing to your JDK installation or have the java executable on your PATH), maven and springframework.


Setting up project in MAven:
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




Since we are developing a web application, we add a spring-boot-starter-web dependency. Before that, we can look at what we currently have by running the following command:

$ mvn dependency:tree

[INFO] com.example:myproject:jar:0.0.1-SNAPSHOT


he mvn dependency:tree command prints a tree representation of your project dependencies. You can see that spring-boot-starter-parent provides no dependencies by itself. To add the necessary dependencies, edit your pom.xml and add the spring-boot-starter-web dependency immediately below the parent section:

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

If you run mvn dependency:tree again, you see that there are now a number of additional dependencies, including the Tomcat web server and Spring Boot itself.


To finish our application, we need to create a single Java file. By default, Maven and Gradle compile sources from src/main/java, so you need to create that directory structure and then add a file named src/main/java/SocialMedia.java to contain the following code:






![crop](https://github.com/Greatnex1/social-media-api/assets/72028378/6cfdb992-8506-40a8-bb9e-4e6ed4113f58)


In the building of this project, i learnt how to use the read_me file, spot bugs quickly and be very observant to details.

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

