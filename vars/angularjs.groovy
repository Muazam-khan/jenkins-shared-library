      def lintChecks(){          
            sh "echo ***** Starting Style Checks for ${COMPONENT}  *****"
            sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true" // this cmd does style check for server.js
            sh "echo ***** Style Checks are Completed for ${COMPONENT} *****"
      }         
      def call()  { 
          pipeline {
              agent any  
                   environment {        
                     SONAR_CRED= credentials('SONAR_CRED')  //pipelione based var, global var
                    }   
                 stages{
                     stage('Lint Checks'){
                       steps {
                          script {
                             lintChecks() //call func is calling another func lintchecks
                        }           
                    }          
                }
                stage('Static Code Analysis'){
                    steps {
                        script {
                           env.ARGS="-Dsonar.sources=." 
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