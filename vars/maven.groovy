      def lintChecks(){          
            sh "echo ***** Starting Style Checks for ${COMPONENT}  *****"
            sh "mvn checkstyle:check || true" // this cmd does style check for server.js
            sh "echo ***** Style Checks are Completed for ${COMPONENT} *****"
      }        
  def call()  { //when u call file nodejs, this function will be called by default, call is default func
          pipeline {
              agent any   
                    tools {
                       maven 'maven-396' // to install maven software with help of tools on jenkins
               }  
                stages{
                    stage('Lint Checks'){
                    steps {
                       script {
                          lintChecks() //call func is calling another func lintchecks
                        }           
                    }          
                }
                stage('Compiling Java Code'){
                    steps {
                        sh "mvn clean compile"
                        sh "ls -ltr target/"
                    }
                }
                stage('Static Code Analysis'){
                    steps {
                        script {
                         env.ARGS="-Dsonar.java.binaries=./target/"   
                         common.sonarChecks() //call func is calling another func lintchecks
                        }            
                   }          
               }
                 stage('Get the Sonar result'){
                    steps {
                        sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gates.sh"
                        sh "bash gates.sh admin password ${SONAR_URL} ${COMPONENT}"
                    }
                 }
               stage('Unit Testing'){
                    steps {
                        script {
                         sh "echo Testing in process" //call func is calling another func lintchecks
                         sh "echo Testing is completed"
                        }            
                   }          
               }
           }
       }  
   }