pipeline {
  agent any
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
      }
    }
    stage("test") {
      steps {
        sh 'mvn test'
      }
    }
    stage("deploy") {
      steps {
        echo 'deploying the application...'
      }
    }
  }
}
