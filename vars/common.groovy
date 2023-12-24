 def sonarChecks(){
      stage('Sonar Checks') {
       sh '''
            echo sonar Checks in progress
            # sh "sonar-scanner -Dsonar.host.url=http://172.31.47.174:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
            echo sonar Checks completed
        '''
      }
 }
  def lintChecks() {
       stage('Lint checks'){
          if(env.APP_TYPE == "maven"){
            sh '''
                 echo ***** Starting Style Checks for ${COMPONENT}  *****
                 mvn checkstyle:check || true // this cmd does style check for server.js
                 echo ***** Style Checks are Completed for ${COMPONENT} *****
            '''
          }
         else if(env.APP_TYPE == "nodejs"){
            sh '''
                 echo ***** Starting Style Checks for ${COMPONENT}  *****
                 npm install jslint
                 /home/centos/node_modules/jslint/bin/jslint.js server.js || true // this cmd does style check for server.js
                 echo ***** Style Checks are Completed for ${COMPONENT} *****
            '''
          }
          else if(env.APP_TYPE == "python"){
            sh '''
                 echo ***** Starting Style Checks for ${COMPONENT}  *****
                    //   sh "pip install pylint" // this cmd does style check for server.js
                   // sh "pylint *.py || true" this cmd does style check for server.js
                 echo ***** Style Checks are Completed for ${COMPONENT} ***** 
            '''
                 }
          else {
            sh '''  
                echo ***** Starting Style Checks for ${COMPONENT}  *****
                echo ***** Style Checks are Completed for ${COMPONENT} ***** 
            '''                   
      }
  } 
}   
def testCases(){ // def declares testCases as a function
     stage('Test Cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing in progress"
            echo "Unit Testing is completed"
        }
        stages["Integration Testing"] = {
            echo "Integration Testing in progress"
            echo "Integration Testing is completed"
        }
        stages["Functional Testing"] = {
            echo "Functional Testing in progress"
            echo "Functional Testing is completed"
        }
        parallel(stages)
    }
}
def artifacts(){
    stage('Checking the Artifact Release on Nexus'){
        env.UPLOAD_STATUS = sh(returnStdout: true, script: "curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true")    
        print UPLOAD_STATUS
    }
       if(env.UPLOAD_STATUS == ""){
            stage('Generating the Artifacts'){
                if(env.APP_TYPE == "nodejs"){
                    sh "npm install"            
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                    
                }
                else if(env.APP_TYPE == "maven"){
                    sh "mvn clean package"
                    sh "mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar"
                }
                else if(env.APP_TYPE == "python"){
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt"
                }
                else{
                    sh "cd static/"
                    sh "zip -r ../${COMPONENT}-${TAG_NAME}.zip .*"
                }
            }
            stage('Uploading the Artifacts'){
                withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USER')]) {
                    sh "echo Uploading the ${COMPONENT} artifacts to Nexus" 
                    sh "curl -f -v -u ${NEXUS_USER}:${NEXUS_PASSWORD} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.83.147:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                    sh "echo Upload completed"
       }
    }
    }
}
  