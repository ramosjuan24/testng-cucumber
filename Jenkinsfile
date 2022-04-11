pipeline {

   agent {
      label 'slave01'
   }
        stages {

             stage('Compile') {

                   steps {
                         echo 'compilando Maven'
                         withMaven(maven: 'apache-maven') {
                              //sh 'mvn clean install -Dmaven.test.failure.ignore test'
                              sh 'mvn clean install -Dmaven.test.failure.ignore test -DsuiteFile=TestNG.xml'
                         }
                   }
             }

             stage('Reports') {

                steps {
                    echo 'Reporte Allure'
                    script {
                            allure([
                                includeProperties: false,
                                jdk: '',
                                properties: [],
                                reportBuildPolicy: 'ALWAYS',
                                //results: [[path: 'target/allure-results']]
                                results: [[path: 'allure-results']]
                            ])
                    }
                }
            }
        }

}