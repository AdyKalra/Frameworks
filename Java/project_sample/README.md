# Test project skeleton for GUI TA

### Required Software and Tools
* Java Version: Oracle Java 8 (Execute **_java -version_** in command line after installation)
* Apache Maven Version: 3.3.X (Execute **_mvn -version_** in command line after installation)
* Integrated Development Environment: Any version IntelliJ Idea or Eclipse

### Conventions followed in this guide
* **{PROJECT_ROOT}** refers to top or root directory of this project

### Compile core modules and install them to local repo
* Open a terminal or command prompt
* Go to **{PROJECT_ROOT}**
* Execute `mvn clean verify -PSingle` for one thread test execution

### Run tests
* Open a terminal or command prompt
* Go to **{PROJECT_ROOT}**
* Execute `mvn clean verify -PSingle` for one thread test execution
* Execute `mvn clean verify -PParallel` for parallel test execution

### Reporting
* Report generating to `functional_autotests/target/site` folder
* Run `index.html` from IDE to see the report

### Logs
* Logs can be found in ``functional_autotests/target/logs folder`