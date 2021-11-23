# Zendesk Code Challenge
### Mingyuan Shan

## Overview: 
This program uses the Zendesk Java Client to interact with the APIs and 
guides the user to view the tickets in a given domain. The user interface
is set up in the command line. This program uses gradle to help with build
and unit testing

## How to run the program:  
The best way to run this program is to use IntelliJ IDEA or your IDE of choice to run TicketViewer.
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
This program uses JUnit to make unit tests. Run individual function in TicketViewerTest to test 
one method at a time. Run the [do All] function in the build.gradle script to generate test report, 
code coverage, as well as javadocs in accessible HTML forms. Using an IDE like IntelliJ is recommended  

Note that you should also supply the IDE with proper environment variables when testing  
Additionally, in order for the test to pass, your domain must have six or more tickets   

You can also build and run the gradlew in the command line. Just remember to set the environment
variables before you do so

## Design decisions
I used the Zendesk Java API Client to avoid re-inventing the wheel.  
The GitHub link is https://github.com/cloudbees-oss/zendesk-java-client  

I chose to download all the tickets at the same time, then close the connection
and start the ticket viewing process. This is done to avoid having to do multiple 
network queries, which can be very slow. This also avoid trying to access tickets 
that may have been deleted. The drawback of this method is that if one client has 
an overwhelmingly large number of tickets, we may run out of memory before we can
start viewing tickets. But for the purposes of this coding challenge, a few hundreds
tickets should be stored with no problem

Per requirement of the spec, if the tickets are more than 25 in number, 
the program will start paging them with 25 tickets on a page. User can either 
choose to view a ticket or continue to the next page



