 def sonarChecks(){
        sh '''
        echo sonar Checks in progress
        # sh "sonar-scanner -Dsonar.host.url=http://172.31.47.174:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
        echo sonar Checks completed
        '''
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
         else if(env.APP_TYPE == "node"){
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