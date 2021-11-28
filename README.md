# Zendesk Code Challenge

### Mingyuan Shan

## Overview:

This program uses the Zendesk Java Client to interact with the APIs and guides the user to view the
tickets in a given domain. The user interface is set up in the command line. This program uses
gradle to help with build and unit testing as well as dependency injection

## How to run the program:

The best way to run this program is to use IntelliJ IDEA or your IDE of choice to run TicketViewer.

Alternatively, you may compile the TicketViewer.java file and run it in the command line.

This program uses dotenv to import the environment variables, please add a .env file containing the
environment variables in the root directory to authenticate yourself

The DOMAIN variable should be your zendesk domain (e.g. https://domain.zendesk.com)  
The EMAIL and TOKEN variables should be your Login Name and API Token

## How to test the program

This program uses JUnit to make unit tests. Using an IDE like IntelliJ is recommended. Run
individual function in TicketViewerTest to test one method at a time. Run the `do All` method in the
build.gradle script to generate test report, code coverage, as well as javadocs in accessible HTML
forms.

Note that you also need proper environment variables in .env when testing  
Additionally, in order for the test to pass, your domain must have six or more tickets

Alternatively, you can build the gradlew in the command line using ./gradlew build

## Design decisions

I used the Zendesk Java API Client to avoid re-inventing the wheel.  
The GitHub link is https://github.com/cloudbees-oss/zendesk-java-client

I chose to download all the tickets at the same time, then close the connection and start the ticket
viewing process. This is done to avoid having to do multiple network queries, which can be very
slow. This also avoid trying to access tickets that may have been deleted. The drawback of this
method is that if one client has an overwhelmingly large number of tickets, we may run out of memory
before we can start viewing tickets. But for the purposes of this coding challenge, a few hundreds
tickets should be stored with no problem

Per requirement of the spec, if the tickets are more than 25 in number, the program will start
paging them with 25 tickets on a page. User can either choose to view a ticket or continue to the
next page

dotenv library is used to facilitate setting up the environment variables

javadocs are used for documentation

## Demo without using zendesk client

In case using zendesk client to access the apis is not the desired practice, I have also included a
snippet of code in class DemoWithoutZendeskClient that accesses and retrieves the necessary data
using HTTP Requests. It retrieves the first 100 tickets and stores the subject in an array list. It
then also prints out the subjects. Since this approach only differs from my main approach in
accessing the apis, the rest of my program can easily be ported over if needed



