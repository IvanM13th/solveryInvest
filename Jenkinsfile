pipeline {
    agent any
    stages {
        stage("build") {
            steps {
                echo 'building the application again...'
            }
        }
        stage("test") {
            steps {
                echo 'testing the application...'
                dir('/var/lib/jenkins/workspace/my-app-pipeline_feature_jenkins/stocks/') {
                    sh 'mvn clean test'
                }
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application...'
            }
        }
        post {
            // Clean after build
            always {
                cleanWs(cleanWhenNotBuilt: true,
                deleteDirs: true,
                disableDeferredWipeout: true,
                notFailBuild: true,
                patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                [pattern: '.propsfile', type: 'EXCLUDE']])
                echo 'cleaning the application...'
            }
        }
    }
}