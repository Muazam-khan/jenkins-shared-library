   def call() {
    node {
        common.lintChecks
        env.ARGS="-Dsonar.java.binaries=./target/" 
        common.sonarChecks()
        common.testCases()
        common.artifacts()
    }
}            
                          
//   def call()  { //when u call file nodejs, this function will be called by default, call is default func
//           pipeline {
//               agent any  
//                  environment {        
//                      SONAR_CRED= credentials('SONAR_CRED')  //pipelione based var, global var
//                      NEXUS= credentials('NEXUS')
//                      SONAR_URL = "172.31.47.174"
//                      NEXUS_URL = "172.31.83.147"
//                       } 
//                     tools {
//                        maven 'maven-396' // to install maven software with help of tools on jenkins
//                  }  
//               stages{
//                     stage('Lint Checks'){
//                     steps {
//                        script {
//                           common.lintChecks() //call func is calling another func lintchecks
//                         }           
//                     }          
//                 }
//                 stage('Compiling Java Code'){
//                     steps {
//                         sh "mvn clean compile"
//                         sh "ls -ltr target/"
//                     }
//                 }
//                 stage('Static Code Analysis'){
//                     steps {
//                         script {
//                          env.ARGS="-Dsonar.java.binaries=./target/"   
//                          common.sonarChecks() //call func is calling another func lintchecks
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
//                 stage('Test Cases'){
//                         parallel {
//                             stage('Unit Testing') {
//                                 steps {
//                                 sh "env" //prints all env variables  
//                                 sh "echo Unit Testing in Progress"
//                                 // sh "mvn test"
//                                 sh "echo Unit Testing is Completed" 
//                                 }
//                             }
//                             stage('Integration Testing') {
//                                 steps {
//                                     sh "echo Integration Testing in Progress"
//                                 // sh "mvn verify"
//                                     sh "echo Integration Testing is Completed"
//                                 }
//                             }
//                             stage('Functional Testing') {
//                                 steps {
//                                     sh "echo Functional Testing in Progress"
//                                 //  sh "mvn function"
//                                     sh "echo Functional Testing is Completed"
//                             }
//                         }
//                       }
//                     }
//                     stage('Checking Artifacts availability on NEXUS') {
//                     when { expression { env.TAG_NAME != null } }
//                     steps { 
//                         script {                      
//                           env.UPLOAD_STATUS = sh(returnStdout: true, script: "curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true")
//                           print UPLOAD_STATUS
//                         }                       
//                       }
//             }
//                 stage('Prepare Artifacts') { // runs only when u run this job from a tag and from branches this should not run
//                        when { 
//                         expression { env.TAG_NAME != null }
//                         expression { env.UPLOAD_STATUS == "" }
//                         }
//                      steps {
//                         sh "echo Preparing artifacts"
//                     }
//                }
//                 stage('Uploading Artifacts') {
//                      when { 
//                         expression { env.TAG_NAME != null }
//                         expression { env.UPLOAD_STATUS == "" }
//                          }
//                      steps {
//                         sh "echo Uploading artifacts"
//                     }
//                 }
//          }
//      }
//   }
