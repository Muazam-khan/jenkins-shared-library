 def sonarChecks(){
        sh "sonar-scanner -Dsonar.host.url=http://172.31.47.174:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password"
      }