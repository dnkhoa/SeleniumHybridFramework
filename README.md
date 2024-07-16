--
# Selenium Automation Framework
An Automation Framework is collection of assumptions,concepts and practices you bring in while developing the automation project, so it helps in constituting a work platform or support for automated testing.It would be great, if the framework is application independent.

If you look into the any existing framework,it will be block of  function libraries for reporting , error handling ,  and driver scripts.So the test automation framework is an execution environment for automated tests. It is the overall system in which our tests will be automated.
#### README Contents
--

- [What is a Framework](#a1)
- [Why do we need Automation framework](#a2)
- [Data-Driven Frameworks](#a3)
- [Keyword-Driven Framework](#a4)
- [Purpose of Framework](#a5)
- [Frameworks Tools](#a6)
- [Framework Structure](#a7) 
    - [Keywords Library](#a7-1)
    - [Object Repository](#a7-2)
    - [Generate reports](#a7-3)
    - [Send reports by Email](#a7-4)
    - [Screenshot](#a7-5)
    - [Driver Script](#a7-6)


#### What is a Framework?
In general, a framework is a real or conceptual structure intended to serve as a support or guide for the building of something that expands the structure into something useful.

###### Prior to knowing about the Hybrid Test Automation Framework, we should know about the existing frameworks. Generally we have, 

 * **Data Driven Framework**
 * **Test Script Modularity Framework**
 * **Keyword Driven Framework**
 * **Test Library Architecture Framework**
 * **Hybrid Framework**
    
#### Why do we need Automation framework?
###### Using Framework, we can solve many issues like

* Writing code once and reusing it. Significant Reduction in Testing Cycle Time
* Running the script with different set of data.
* Executing the scripts end-to-end without any manual intervention. ( If any error occurs from tool or application, Script run will stop. If we use framework, it will skip or fail that testcase and run with the next testcase.)
* With basic knowledge on tool also anyone can run and write the script. (All the script, Keywords has been written by experts, we have to know how to use those keywords)
* Can able to run scenarios by selecting YES or NO. (Modular Driven Framework)
* Maintenance becomes very easy.

##### Combination of above all framework is nothing but Hybrid Driven Framework.

###### Example: In an Timetracker application, We have 5 scenarios.
* Login into application and create a Project and logout
* Login into application and create Modules under project and logout
* Login into application and create different users( Approver, Verify, Normal) and logout
* Login into application create timesheets and logout
* Login into application and delete timesheets and logout

###### Now write a script for all 5 scenarios using any automation tool. 

###### If we write a script for all 5 scenarios, it will become a big script and we are repeating the same script again and again. So to overcome this, we have to go for Function Driven Framework

**Function Driven Framework:** Dividing the scripts into functions and reusing them.

###### Write a script for one time. Make it as a function and reuse the same function

* Read all the scenarios.
* Identify the repeated steps.
* Convert them into functions

###### Examples: Login(), Logout(), CreateProject()�. Functions

**Advantages :**

* Write once (saves time)
* Reusable
* Easy Maintenance

**Disadvantages :**

* Data is hardcoded, we can�t run with multiple sets of data.
* Data Driven Framework: is a framework where test input and output values are read from data files (Excel, CSV, Database) and are loaded into variables in captured or manually coded scripts.
If we see the above example, For Login(uname) we can run the script with any data picking it from excel or CSV.


### Data-Driven Frameworks
--

A data-driven framework is where test input and output values are read from data files (ODBC sources, CVS files, Excel files, DAO objects, ADO objects, and such) and are loaded into variables in captured or manually coded scripts. In this framework, variables are used for both input values and output verification values. Navigation through the program, reading of the data files, and logging of test status and information are all coded in the test script. This is similar to table-driven testing (which is discussed shortly) in that the test case is contained in the data file and not in the script; the script is just a "driver," or delivery mechanism, for the data. In data-driven testing, only test data is contained in the data files. 

### Keyword-Driven Framework
--

This requires the development of data tables and keywords, independent of the test automation tool used to execute them and the test script code that "drives" the application-under-test and the data. Keyword-driven tests look very similar to manual test cases. In a keyword-driven test, the functionality of the application-under-test is documented in a table as well as in step-by-step instructions for each test. In this method, the entire process is data-driven, including functionality.

Before getting into the framework, lets discuss on what are the external files which will be required. The list of files that will be required in addition to selenium client driver and selenium server jar files are as below

* **TestNG**: in order to data drive our test, we would require the latest version of testNG jar file which is now testng-5.14.1.jar and can be downloaded from http://testng.org/doc/download.html

* **JXL**: in order to use Microsoft Excel files as data source we would need the jxl jar file which can be downloaded from http://sourceforge.net/projects/jexcelapi/files/

* **Latest Junit**: this will be required to verify certain condition and can be downloaded from https://github.com/KentBeck/junit/downloads

* **TestNG plugin for Eclipse**: This will be required to run the TestNG scripts in eclipse and the content of this plugin has to be placed inside the dropin folder inside the eclipse directory. The file can be downloaded from http://testng.org/doc/download.htmlframeworks

### Purpose:
--
To build a Hybrid Test Automation Framework which can be used across different web based applications. In this approach, the endeavor is to build a lot of applications independent reusable keyword components so that they can directly used for another web application without spending any extra effort. With this framework in place, whenever we need to automate a web based application, we would not need to start from scratch, but use the application independent keyword components to the extent possible and create application specific components for the specific needs.

### Tools
-- 

 * **IntelliJ IDE** - Best Java IDE in the Whole Wide World!!!
 * **Eclipse Java EE**
 * **Java 7+**
 * **WebDriverJs** - JavaScript
 * **MySQL Connector**
 * **WebDriver 2+**
 * **Selenium Grid** - Not implemented yet!!
 * **SauceLabs** - Not Implemented yet!!
 * **Object Repository** - Not Implemented yet!!
 * **Selenium Server**
 * **Apacha POI**
 * **Apache Ant**
 * **Apache Maven**
 * **JUnit 4.10**
 * **TestNG**
 * **Jenkins** (Hudson) - Not implemented yet!!
 * **XML**
 * **Log4j**
 * **Others Open-Source tools**

### Framework Structure:
--

The framework consists of the following components.


- **com.totsy.config**
    - config.properties
    - config.or      
- **com.totsy.log**
    - Application.log       
- **com.totsy.test**
    - Constants.java
    - DriverScript.java
    - Keywords.java
	- GetOSName.java
- **com.totsy.utils** 
    - ReportUtil.java
    - SendMail.java
    - Zip.java 
- **com.totsy.xls** 
    - FixedPurchase.xlsx
    - Order.xlsx
    - OrderWorkFlow.xlsx
    - Product.xlsx
    - User.xlsx
    - Suite.xlsx
- **test(Sample scripts)** 
    - Arraylist.java

### Keywords Library:
--

### Object Repository:
--
All the element locator are kept in the Object Repository folder.

### Generate reports:
--

### Send reports by Email:
--

### Screenshot
--

### Driver Script:
--
