      def lintChecks(){          
            sh "echo ***** Starting Style Checks for ${COMPONENT}  *****"
             //   sh "pip install pylint" // this cmd does style check for server.js
          // sh "pylint *.py || true" this cmd does style check for server.js
            sh "echo ***** Style Checks are Completed for ${COMPONENT} *****"
      }             
      def call()  { //when u call file nodejs, this function will be called by default, call is default func
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
                  stage('Test Cases'){
                    parallel {
                        stage('Unit Testing') {
                            steps {
                                sh "echo Unit Testing in Progress"
                               // sh "ply test"
                                sh "echo Unit Testing is Completed" 
                            }
                        }
                        stage('Integration Testing') {
                            steps {
                                 sh "echo Integration Testing in Progress"
                               // sh "ply verify"
                                 sh "echo Integration Testing is Completed"
                            }
                        }
                        stage('Functional Testing') {
                            steps {
                                 sh "echo Functional Testing in Progress"
                              //  sh "ply function"
                                 sh "echo Functional Testing is Completed"
                           }
                       }
                   }
                }
             }
          }
      }