def call() {
    node {
        common.lintChecks
        env.ARGS="-Dsonar.sources=." 
        common.sonarChecks()
        common.testCases()
    }
}        

//      def call()  { 
//           pipeline {
//               agent any 
//                   environment {                             
//                       SONAR_CRED= credentials('SONAR_CRED')  //pipelione based var, global var
//                       NEXUS= credentials('NEXUS')
//                       SONAR_URL = "172.31.47.174"
//                       NEXUS_URL = "172.31.83.147" 
//                     }    
//                  stages{
//                      stage('Lint Checks'){
//                        steps {
//                           script {
//                              common.lintChecks() //call func is calling another func lintchecks
//                         }           
//                     }          
//                 }
//                 stage('Static Code Analysis'){
//                     steps {
//                         script {
//                            env.ARGS="-Dsonar.sources=." 
//                            common.sonarChecks() //call func is calling another func lintchecks
//                         }            
//                    }          
//                }
//                  stage('Get the Sonar result'){
//                     steps {
//                         sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gates.sh"
//                         // sh "bash gates.sh admin password ${SONAR_URL} ${COMPONENT}"
//                         sh "echo Scan is Good"
//                     }
//                  }
//                stage('Test Cases'){
//                     parallel {
//                         stage('Unit Testing') {
//                             steps {
//                                 sh "env" //cmd will publish all env variables
//                                 sh "echo Unit Testing in Progress"
//                                // sh "npm test"
//                                 sh "echo Unit Testing is Completed" 
//                             }
//                         }
//                         stage('Integration Testing') {
//                             steps {
//                                  sh "echo Integration Testing in Progress"
//                                // sh "npm verify"
//                                  sh "echo Integration Testing is Completed"
//                             }
//                         }
//                         stage('Functional Testing') {
//                             steps{
//                                  sh "echo Functional Testing in Progress"
//                               //  sh "npm function"
//                                  sh "echo Functional Testing is Completed"
//                            }
//                        }
//                    }
//                 }
//                 stage('Checking Artifacts availability on NEXUS') {
//                     when { expression { env.TAG_NAME != null } }
//                     steps { 
//                         script {                      
//                           env.UPLOAD_STATUS = sh(returnStdout: true, script: "curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true")
//                           print UPLOAD_STATUS
//                         }                       
//                       }
//             }
//                 stage('Prepare Artifacts') {
//                     when { 
//                     expression { env.TAG_NAME != null } 
//                     expression { env.UPLOAD_STATUS == "" }
//                     }
//                     steps {                       
//                        sh "npm install"
//                        sh "ls -ltr"
//                        sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
//                        sh "ls -ltr"                              
//                       }
//             }
//                 stage('Uploading Artifacts') {
//                     when { 
//                         expression { env.TAG_NAME != null }
//                         expression { env.UPLOAD_STATUS == "" }
//                          } 
//                     steps {                                            
//                         sh "echo Uploading ${COMPONENT} artifacts"
//                         sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.83.147:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
//                         }
//                    }
//                 }
//           }
//   }
            




            