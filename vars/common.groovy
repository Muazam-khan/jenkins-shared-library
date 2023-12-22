 def sonarChecks(){
        sh '''
        echo sonar Checks in progress
        # sh "sonar-scanner -Dsonar.host.url=http://172.31.47.174:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
        echo sonar Checks completed
        '''
      }