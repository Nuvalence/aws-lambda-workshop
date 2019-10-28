# Using Lambda with DynamoDB and SQS

In this section we will be set up an SQS queue which will trigger our Lambda when a message is added

- Create and Configure Lambda function
- Create an SQS queue and connect it to the Lambda function


## Implementing the Lambda Handler
Implement a handler `SqsMessageHandler.java` that takes as input an `SQSEvent` and writes all of its messages (where each message is a json formatted `FavoriteColorModel`) to a DynamoDB table using the provided `DynamoWriter`. 
[An example solution](SqsMessageHandler.java) has been provided. 

## Create a Lambda Function
First create another new Lambda function named `nuvalence-workshop-sqs`, upload the zip file we used before, and attach 
the `nuvalence-table-access` policy to the new `nuvalence-workshop-sqs-role` that was created. This is so that we can use a different handler without breaking our integration with API Gateway.  

## Configure Lambda function
Before our Lambda function can be triggered from an SQS message we must first give it permission to interact with SQS. 
Luckily AWS already has a policy that allows this so we don't have to make a custom policy like we did for DynamoDB.
1. On the Lambda function page update the handler to `io.nuvalence.workshops.aws.lambda.SqsMessageHandler::handleRequest` and then click "Save".
2. We can then take a shortcut to the Lambda execution role by scrolling down to the "Execution Role" section and clicking the link that says "View the nuvalence-workshop-sqs-role role on the IAM console".
3. Now click "Attach Policy" and attach "AWSLambdaSQSQueueExecutionRole" to give the Lambda function permission to interact with an SQS queue.

## Creating an SQS Queue
1. To create an SQS queue start by navigating to "Services" -> "Simple Queue Service" -> "Create New Queue".
2. For our use case a standard queue is fine, name the queue `nuvalence-queue` and then click "Quick-Create Queue".
3. Next select the queue and click "Queue Actions" then "Configure Trigger for Lambda Function".
4. In this menu select "nuvalence-workshop-sqs" and then click "Save".

## Testing the Queue
1. To test our queue and it's connection to the Lambda function click "Queue Actions" then "Send a Message".
2. In this menu send a message containing the following JSON:
```
{
    "name": "<your name> SQS",
    "color": "<any color>"    
}
```
3. Now check the DynamoDB table to see the new data.

