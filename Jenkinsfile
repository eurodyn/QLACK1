pipeline {
    agent {
        docker {
            image 'eddevopsd2/maven-java-npm-docker:mvn3.6.3-jdk11-npm6.14.4-docker'
            args '-v /root/.m2/Qlack1-GIT:/root/.m2'
        }
    }
    options { 
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10')) 
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Dependencies Check') {
            steps {
                sh 'mvn org.owasp:dependency-check-maven:aggregate'
            }
        }
        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('sonar'){
                    sh 'mvn sonar:sonar -Dsonar.projectName=Qlack1-Git -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_KEY_QLACK1}'
                }
            }
        }
        stage('Produce bom.xml'){
            steps{
                sh 'mvn org.cyclonedx:cyclonedx-maven-plugin:makeAggregateBom'
            }
        }
        stage('Dependency-Track Analysis'){
            steps{
                sh '''
                    cat > payload.json <<__HERE__
                    {
                        "project": "a09a05f6-4a66-4791-bbda-c8d4253c5fd4",
                        "bom": "$(cat target/bom.xml |base64 -w 0 -)"
                    }
                    __HERE__
                   '''

                sh '''
                    curl -X "PUT" ${DEPENDENCY_TRACK_URL} -H 'Content-Type: application/json' -H 'X-API-Key: '${DEPENDENCY_TRACK_API_KEY} -d @payload.json
                   '''
            }
        }
    }
    post {
        changed {
            emailext subject: '$DEFAULT_SUBJECT',
                body: '$DEFAULT_CONTENT',
                to: 'qlack@eurodyn.com'
        }
    }
}