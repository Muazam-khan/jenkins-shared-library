# jenkins-shared-library

This is a repository to host all the common patterns that arise during the CI/CD of roboshop components development.
....
This jenkins-shared-libary vars/ is going to host all the common stages and we are going to import this library inturn the functions to keep the code DRY.
....

Ref : https://www.jenkins.io/doc/book/pipeline/shared-libraries/ 

## Why Shared Libraries in Jenkins ?

As Pipeline is adopted for more and more projects in an organization, common patterns are likely to emerge. Oftentimes it is useful to share parts of Pipelines between various projects to reduce redundancies and keep code "DRY" [1].

    Pipeline has support for creating "Shared Libraries" which can be defined in external source control repositories and loaded into existing Pipelines.

## Shared Library Structure

Directory structure The directory structure of a Shared Library repository is as follows:

The directory structure of a Shared Library repository is as follows:

....
      (root)
      +- src                     # Groovy source files
      |   +- org
      |       +- foo
      |           +- Bar.groovy  # for org.foo.Bar class
      +- vars
      |   +- foo.groovy          # for global 'foo' variable
      |   +- foo.txt             # help for 'foo' variable
      +- resources               # resource files (external libraries only)
      |   +- org
      |       +- foo
      |           +- bar.json    # static helper data for org.foo.Bar
.....

     The src directory should look like standard Java source directory structure. This directory is added to the classpath when executing Pipelines.

        The vars directory hosts script files that are exposed as a variable in Pipelines. The name of the file is the name of the variable in the Pipeline. So if you had a file called vars/log.groovy with a function like def info(message)…​ in it, you can access this function like log.info "hello world" in the Pipeline. You can put as many functions as you like inside this file. Read on below for more examples and options.

#### Security Scans

     Scans are of 2 types :
        1) SAST   ( Analysing the code )
        2) DAST   ( Analysing the application through the endPoint / pen testing ) // pen testing means penetration testing

#### Why do i need Static Code Analysis (SAST : SonarQube )
....
      1) Determines the overall code quality.
      2) Identifies the hotSpots in the code ( Hotspot : Any sensitivie information in the code ).
      3) Code Standards and the package versions to be used can be controlled.
      4) Identifies the duplicate patterns in the code.
      5) Overall Code Quality Standard.
....

### SonarQube can be utilized in any of the 2 ways
....
    1) Create your own server and set-up sonarQube on the top of that server ( Paid tool : 1 month : In free version, you'd get embedded Portgress DB ) 

    2) You can use SAS offering ( You don't have to host anything locally )
....  // u get offering from a provider, u get a username and password

#### Maven Goals
....
    A Maven phase represents a stage in the Maven build lifecycle. Each phase is responsible for a specific task.

    Here are some of the most important phases in the default build lifecycle:

    Validate: check if all information necessary for the build is available

    compile: compile the source code

        test-compile: compile the test source code

        test: run unit tests

    Package: package compiled source code into the distributable format (jar, war, …)
    integration-test: process and deploy the package if needed to run integration tests

    install: install the package to a local repository
    deploy: copy the package to the remote repository
    For the full list of each lifecycle’s phases, check out the Maven Reference.

    Phases are executed in a specific order. This means that if we run a specific phase using the command: 
....
    
### How to do Unit Testing & Ingeration Testing
....
     Both these test cases are typically placed in the same code where your applications is hosted. And can be called by the same build tool using test or verify commands.

    If you're node based project :
    ex : npm test    [ Unit Testing ] //cmd
         npm verify  [ Integration Testing] //cmd

....
### How to add your Jenkins Job The Ability To Run the Job From a particular branch or from Tag ? 
.....


.....


### What is the versioning strategy we are going through ???/
...
   We are going with Git Semantic Versioning
...

#### How to create a Git Tag ?

...
    Tags are typically created against MAIN Branch only.

                $ git tag (0.0.0) // this cmd creates tag 0.0.0 or any tag nos u want to associate
                $ git push --tags  //pushes the created tags
...
    

## 