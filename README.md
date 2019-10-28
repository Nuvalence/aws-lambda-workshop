# Nuvalence Serverless Workshop Exercise

This workshop will serve as an introduction to Lambda, which is Amazon's serverless computing service. We'll first cover 
creating and deploying a basic Lambda function written in Java. Then we'll focus on connecting our Lambda function to other AWS services, 
specifically DynamoDB, API Gateway, and Simple Queue Service (SQS). All of these steps are achievable using the Amazon CLI, 
however for this workshop we will be using the AWS console. Throughout this workshop you'll be creating and naming multiple new resources. 
To keep track of everything there will be recommended names which I will use to refer to the resources. Using the recommended
names is not required, but will make the workshop easier to follow. 
It is also recommended to do this workshop in order, without skipping sections, as instructions will not be as explicit for steps that have been done before in earlier sections.

### Prerequisites:

- Java 8
- Amazon Web Services (AWS) Account
- Postman or curl

### Workshops:

- [Hello World Function](workshops/hello-world-function) - a simple introduction to using AWS Lambda for the first time
- [Using Lambda with DynamoDB and API Gateway](workshops/dynamodb-and-api-gateway-integration) - integrating your function with a serverless database and API. 
- [Using SQS](workshops/sqs-integration) -  integrating your function with SQS to receive requests from the queue. 

### Clean up resources:
Throughout this workshop we created a number of different resources, be sure to delete them once done to avoid unnecessary costs.
   - nuvalence-queue: SQS -> "Queue Actions" -> "Delete Queue"
   - nuvalence-api: API Gateway -> "Actions" -> "Delete API"
   - nuvalence-table: DynamoDB -> "Delete table"
   - nuvalence-table-access: IAM -> "Delete policy"
   - nuvalence-workshop-role: IAM -> "Delete role"
   - nuvalence-workshop: Lambda -> "Actions" -> "Delete function"
   - nuvalence-hello-world-role: IAM -> "Delete role"
   - nuvalence-hello-world: Lambda -> "Actions" -> "Delete function"
   - nuvalence-workshop-sqs-role: IAM -> "Delete role"
   - nuvalence-workshop-sqs: Lambda -> "Actions" -> "Delete function"
