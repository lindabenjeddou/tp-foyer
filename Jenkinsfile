pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out code from Git"
                checkout scm
            }
        }

        stage('MVN Build') {
            steps {
                echo "Running Maven build"
                sh 'mvn clean install'
            }
        }

        stage('JDK Test') {
            steps {
                echo "Testing JDK Installation"
                sh '''
                # Print Java version to verify JDK installation
                java -version

                # Print JAVA_HOME to ensure it's set correctly
                echo $JAVA_HOME
                '''
            }
        }

        stage('Code Quality Test') {
            steps {
                withSonarQubeEnv('Sonar') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://172.17.0.2:9000 -Dsonar.login=squ_bdf4362b8971c688e1ac355bd433ff1a1664f75d'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
    }
}
