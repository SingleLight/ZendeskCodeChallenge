# Zendesk Code Challenge
#### Mingyuan Shan

### Overview: 
This program uses the Zendesk Java Client to interact with the APIs and 
guides the user to view the tickets in a given domain. The user interface
is set up in the command line.  

### How to run the program:  
The best way to run this program is to use IntelliJ IDEA or your IDE of choice.
Set up the environment variables of DOMAIN, EMAIL and TOKEN in the application 
configuration to provide proper authentication.
  
Alternatively, you may compile the TicketViewer.java file and run it in 
the command line. But do note that you also need to set up the proper environment
variables of DOMAIN, EMAIL and TOKEN. Or at least swap these values with your credentials
in the source code before you compile.  

The DOMAIN variable should be your zendesk domain  
e.g. https://domain.zendesk.com  
The EMAIL and TOKEN variables should be your Login Name and API Token  

## How to test the program  
This program uses JUnit to run unit tests. Run the [do All] function in the
build.gradle script to generate test report, code coverage, as well as javadocs 
in accessible HTML forms  





