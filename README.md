Prerequisites: 
Java 11:
Installation instructions - https://docs.oracle.com/goldengate/1212/gg- winux/GDRAD/java.htm#BGBFHBEA 

Postman:
Installation instructions - https://www.postman.com/

Starting the application:  
Windows:
1. Download BankingApplication-1.0.0.jar
2. Hit Windows button + R and a ‘Run’ window will appear on the bottom left part of your screen
3. Type in ‘cmd’ in the run window
4. A command prompt will open on your screen. Navigate to the folder where you saved the jar
5. Type in the command prompt ‘java -jar BankingApplication-1.0.0.jar’
6. After a few seconds, the app will start running. Open Postman and you can start using it. For instructions on how to use the app, refer to the ‘How to use’ section.

Mac OS:
1. Download BankingApplication-1.0.0.jar
2. Hit command + space, type in ‘terminal’ and hit enter
3. Navigate to the folder where you saved the jar
4. Type in ‘java -jar BankingApplication-1.0.0.jar’
5. After a few seconds, the app will start running. Open Postman and you can start using it. For instructions on how to use the app, refer to the ‘How to use’ section.


HOW TO USE:  
Once the application is running, it exposes two endpoints:
1. http://localhost:8080/api/v1/sendMoney - this endpoint is used to send money. Send a POST request through Postman which contains the following body:  
    {  
        "fromAccount": 1, -- expects an integer; indicates the account ID FROM which we are sending money  
        "toAccount": 0, -- expects an integer; indicates the account ID TO which we are sending money  
        "amount": 1 -- expects a positive number with maximum of 2 digits to the right of the decimal point; indicates the amount of money we want to send  
    }  
2. http://localhost:8080/api/v1/accountBalance/{accountId} - this endpoint is used to display account balance and a list of transactions. Send a GET request and change {accountId} to the ID of the account you want to see.  

A console for the database used can be found at http://localhost:8080/h2-console/ . You can access it only if the app is running. To access H2, fill in the below details on the screen that appears:  
Driver class: org.h2.Driver  
JDBC URL: jdbc:h2:mem:testdb  
User Name: sa  
Password: sa  
  
When the app is ran, two accounts will be created. One with ID as '0' and one with ID as '1'. Both have positive balances and can be used to test the functionality.