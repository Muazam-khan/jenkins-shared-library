      def lintChecks('component'){          
            sh "echo ***** Starting Style Checks for ${component} *****"
            sh "ls -ltr server.js"
            sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true" // this cmd does style check for server.js
            sh "echo ***** Completed Style Checks *****"
      }     

      def call()  { //when u call file nodejs, this function will be called by default
            pipeline {
            agent any     
            stages{
            stage('Lint Checks'){
                steps {
                    script {
                        nodejs.lintChecks("component")
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