pipeline { 
    agent any  
    stages { 
        stage('Compile') { 
            steps { 
               withMaven {
    				sh "mvn clean compile"
				}
            }
        }
        stage('Build') { 
            steps { 
               withMaven {
					sh "mvn install -Dmaven.test.skip=true"
				}
            }
        }
        stage('Install') { 
            steps { 
               withMaven {
					sh "cd ./bike && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
			   withMaven {
					sh "cd ./bike-api && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
			   withMaven {
					sh "cd ./bike-authority && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
			   withMaven {
					sh "cd ./bike-exporter && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
			   withMaven {
					sh "cd ./bike-reporter && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
			   withMaven {
					sh "cd ./bike-wallet && mvn package docker:build -DpushImage -Dmaven.test.skip=true"
				}
            }
        }
    }
}