pipelineJob('continuous-integration') {
    definition {
        cps {
            script('''
                        pipeline {
                            agent any

                            tools {
                                // Install the Maven version configured as "M3" and add it to the path.
                                maven "M3"
                            }

                            environment {
                                SONAR_TOKEN = credentials('sonarqube_token') // Load the credential into an environment variable
                            }

                            stages {
                                stage('SCM') {
                                    steps {
                                        // Checkout code from SCM
                                        git branch: 'main', url: 'https://github.com/Erelip/continuous-integration'
                                    }
                                }
                                stage('Build') {
                                    steps {
                                        // Run Maven to build the project
                                        sh "mvn -Dmaven.test.failure.ignore=true clean package"
                                    }
                                    post {
                                        success {
                                            // If Maven was able to run the tests, record the test results and archive the jar file.
                                            junit '**/target/surefire-reports/TEST-*.xml'
                                            archiveArtifacts 'target/*.jar'
                                        }
                                    }
                                }
                                stage('SonarQube Analysis') {
                                    steps {
                                        script {
                                            // Run SonarQube analysis using Maven
                                            def scannerHome = tool 'sonarqube'
                                            withSonarQubeEnv('sonarqube') {
                                                sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=continuous-integration -Dsonar.projectName='continuous-integration' -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=${SONAR_TOKEN} -Dsonar.java.binaries=target/classes"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    '''.stripIndent())
            sandbox(true)
        }
    }
}