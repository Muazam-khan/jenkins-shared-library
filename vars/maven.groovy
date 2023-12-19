      def lintChecks(){          
            sh "echo ***** Starting Style Checks for ${COMPONENT}  *****"
            sh "mvn checkstyle:check || true" // this cmd does style check for server.js
            sh "echo ***** Style Checks are Completed for ${COMPONENT} *****"
      }     

      def call()  { //when u call file nodejs, this function will be called by default, call is default func
            pipeline {
              agent any     
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
                       sh "echo ***** Starting Static Code Analysis *****"          
                   }          
               }
           }
       }  
   }