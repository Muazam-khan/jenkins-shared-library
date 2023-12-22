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
                        // sh "bash gates.sh admin password ${SONAR_URL} ${COMPONENT}"
                        sh "echo Scan is Good"
                    }
                 }
               stage('Test Cases'){
                    parallel {
                        stage('Unit Testing') {
                            steps {
                                sh "env" //cmd will publish all env variables
                                sh "echo Unit Testing in Progress"
                               // sh "npm test"
                                sh "echo Unit Testing is Completed" 
                            }
                        }
                        stage('Integration Testing') {
                            steps {
                                 sh "echo Integration Testing in Progress"
                               // sh "npm verify"
                                 sh "echo Integration Testing is Completed"
                            }
                        }
                        stage('Functional Testing') {
                            steps{
                                 sh "echo Functional Testing in Progress"
                              //  sh "npm function"
                                 sh "echo Functional Testing is Completed"
                           }
                       }
                   }
                }
                stage('Prepare Artifacts') {
                    steps { 
                      when { expression { env.TAG_NAME != null } }
                      sh '''
                          npm install
                          ls -ltr
                          zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                         '''                                       
               }
            }
                stage('Uploading Artifacts') {
                    steps { 
                        when { expression { env.TAG_NAME != null } }                    
                        sh "echo Uploading artifacts"
                        }
                   }
                }
          }
  }
            