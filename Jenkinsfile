pipeline {
  agent any
  stages {
    stage('Sniff versions') {
      agent any
      steps {
        sh '''                        jenkins.get().getView(\'App\').items.each() {
                            println it.fullName
                        }
'''
        }
      }
    }
  }