# Using Lambda with DynamoDB and API Gateway

## DynamoDB Integration
In this section we will integrate DynamoDB with our Lambda function.
- Creating a table in DynamoDB
- Configuring a role to allow read/write access to that table

## Implementing the Lambda Handler
Implement a handler `FavoriteColorRequestHandler.java` that takes as input a `FavoriteColorModel` and writes to a DynamoDB table using the provided `DynamoWriter`. 
[An example solution](FavoriteColorRequestHandler.java) has been provided. 

### Creating a Lambda Function
Before starting this section create a new Lambda function named `nuvalence-workshop` using the same steps found in the previous 
"Creating a Lambda Function" section. Then upload the same zip we uploaded earlier and leave the default settings, we'll update those later before testing our new Lambda function.

### Creating a Table
1. In the console navigate to "Services" -> "DynamoDB" -> "Create Table" to create a new DynamoDB table. 
2. Name the table `nuvalence-table` and confirm that "Use default settings" is selected under "Table settings". 
3. Next we'll define some attributes of the table. Set "Partition key" to `name` and select "String" from the dropdown menu. Used alone a partition key is basically a primary key, meaning it must be 
unique for each item in the table. 
4. Names aren't guaranteed to be unique so click "Add sort key" to add a second field which will be used with the partition key to make a composite key. Set the sort key to `date` and select "Number" from the dropdown.
Now we have a composite key of name and date, which will be unique across all items. 
5. Click "Create" to make the table, then scroll down in the "Overview" and copy the table's Amazon Resource Name (ARN) to use later.
I would recommend leaving this table open in a separate browser tab since you'll want to confirm that data from later steps are correctly being added.
 
### Configuring a Role to Access DynamoDB
Now that we have a DynamoDB table to put data in we need to give the Lambda function access to it. First we're going to create a new
policy with access to write items to the DynamoDB table.
1. Navigate to "Services" -> "IAM" -> "Policies" -> "Create Policy" to start.
2. Once in the "Create Policy" page click on the JSON tab and replace the template with this JSON:
    ```
    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Action":"dynamodb:PutItem",
                "Resource":"<DYNAMO TABLE ARN>"
            }
        ]
    }
    ```
    This JSON policy means that we will allow any role with this policy attached to preform the "dynamodb:PutItem" action
on the DynamoDB table we created.
3. Click "Review policy", name the policy `nuvalence-table-access` and then "Create policy".

    Now that we have a policy we are going to attach it to the Lambda function's execution role.

4. Start by going back to "Services" -> "IAM" -> "Roles" and search for the execution role which will be called `nuvalence-workshop-role-xxxxxxxx`.
5. Once you've found it click on the role, then "Attach policies". 
6. Now search for and select `nuvalence-table-access` and then click "Attach policy".

### Updating Lambda Settings
Now that our Lambda function has access to the table, navigate back to the function and confirm that "Amazon DynamoDB" appears
in the "Designer" section. We just need to update some settings before starting to test.
1. First change the handler to `io.nuvalence.workshops.aws.lambda.FavoriteColorRequestHandler::handleRequest`
    
    If you're interested in looking at the code you'll notice the handler doesn't do anything besides call a separate method to
write the data to DynamoDB. This is because we don't need to worry about decrypting the data or making any other transformations 
before passing it to a secondary method. However we'll see that this isn't always the case later.
2. Since we are doing a more demanding task than just returning a string increase the memory allocated to 512 MB and the timeout to 15 seconds. 
3. The method that posts items to DynamoDB uses an environment variable to set the table name, so we need to add a new environment variable named `TABLE_NAME`
with the value set to `nuvalence-table`. 
4. Then save the changes with the "Save" button in the top right.

### Testing Your Function
To test our updated Lambda function configure and run a new test event with the following JSON:
```
{
    "name":"<your name>",
    "color":"<any color>"
}
```
To see if the test data was posted to DynamoDB navigate back to DynamoDB, select `nuvalence-table`, and then click on the "Items"
tab to view the data in the table. You should see the data from the test event with an additional `date` field that contains a
timestamp of when the data was added.

## API Gateway Integration:

In this section we will set up an API Gateway to trigger our Lambda function.
- Create an API using API Gateway
- Connecting the API to our Lambda function
- Deploy the API

### Creating an API with API Gateway

Now that we have a working Lambda function that will take JSON and post it to a DynamoDB table, it's time to expose an API endpoint to trigger our function.
1. To create an API click on "Services" -> "API Gateway" -> "Create API".
2. For our use case the default settings are fine, name the API `nuvalence-api` then click "Create API".
3. Next click the "Actions" dropdown and then "Create Resource".
4. Set "Resource Name" to `nuvalence-demo` and click the checkbox next to "Enable API Gateway CORS"
5. Now set "Resource Path" to `demo`, this sets the actual endpoint that we'll use.

    The next step is to define what methods are allowed and what they each do.  Since our Lambda function takes input data
    and posts it to DynamoDB the only method we need to configure is "POST".
     
6. To set this up click the "Actions" dropdown again and then "Create Method" 
7. Click on the blank dropdown menu below "/demo" and select "POST", then confirm with the check mark.
8. In the "Setup" page connect this method to the Lambda function by setting "Lambda Function" to `nuvalance-workshop`, then click "Save" and "OK" 
    
   
### Testing API in AWS Console
1. To test your endpoint using the AWS console click on "Test" with the lightning bolt below it on the "Method Execution" page.
If you aren't already on this page just click on "POST" below "/demo" in the sidebar to access it.
2.  In the request body write JSON that will be sent to the Lambda function in this format:
    ```
    {
        "name":"<your name> API",
        "color":"<any color>"
    }
    ```
3. Next click "Test" and navigate to your DynamoDB table to check the data.

### Deploying the API
1. To deploy our API select "Deploy API" from the "Actions" dropdown.
2. In this page select "[New Stage]" in the "Deployment stage" dropdown and more settings will appear.
3. Set "Stage name" to `test`, this will append "test" to the end of the URL endpoint that will be created when you deploy. 
In other use cases this can be helpful for having multiple versions of an API available at once (e.g. dev, test, prod).
4. Once you click "Deploy" you should see an "Invoke URL" displayed at the top of the page. This is the URL where your API
is available to call and it should look like `https://xxxxxxxx.execute-api.us-east-1.amazonaws.com/test`.
5. Copy the "Invoke URL" to use in the next step when testing.

### Testing the API with Curl (or Postman)
To test our deployed API we'll be making a request to the endpoint using Curl, however if you prefer Postman you can use that instead.
In a commandline window write the following command to make a POST request that sends a request body containing JSON:
```
curl -d '{"name":"<your name> Deployed", "color":"<any color>"}' -X POST <INVOKEURL>/demo
```
Once the request has completed go back to your DynamoDB table to check the data.
