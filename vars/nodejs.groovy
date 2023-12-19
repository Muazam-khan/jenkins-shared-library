      def lintChecks(){          
            sh "echo ***** Starting Style Checks *****"
            sh "ls -ltr server.js"
            sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true" // this cmd does style check for server.js
            sh "echo ***** Completed Style Checks *****"
      }       