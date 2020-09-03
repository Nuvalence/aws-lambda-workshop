# Hello World Lambda Function

In this section we will be creating a basic Lambda function in the AWS console.
 - Setting up a Lambda function
 - Building a Lambda archive
 - Configuring a Lambda handler
 - Testing a Lambda function via the AWS Console
 
## Implementing the Lambda Handler
Implement a handler `HelloWorldHandler.java` or `index.js` that takes as input a `String` and returns a `String` formatted `"Hello ${input}."`. 
[Two example solutions](HelloWorldHandler.java)(index.js) have been provided. 

## Creating a Lambda Function
The first step in the process is to create a new Lambda function. 

 1. Log in to the AWS console and navigating to 
"Services" -> "Lambda" -> "Create Function". 
 2. The code samples in this workshop are written in Java, so select the Java 8 runtime (or Node if you are using the JavaScript solutions). 
 3. Set the name to `nuvalence-hello-world` or a custom name using only letters, numbers, hyphens, or underscores.
 4. Expand the "Choose or create an execution role" section and confirm that "Create a new role with basic Lambda permissions" is the selected option. This will 
create a new role for the function and automatically attach a policy which allows the Lambda function to write logs to Amazon CloudWatch. 
 5. Click "Create Function".

## Building a Lambda Function

[Note: You can skip this if you are using JavaScript!]

Now that we have a Lambda function created we need to upload code for it to execute when triggered. To do this we'll use gradle
to build a code archive. 

Below is a snippet from the [build script](./build.gradle). 
```
task buildZip(type: Zip) {
    from compileJava         // include the compiled source code
    from processResources    // include any resources
    into('lib') {            // in a lib folder at the root of the archive, include all runtime dependencies
        from configurations.runtimeClasspath
    }
}
```

It is important to note rather than building a fat jar, we've included all of our dependencies in a lib folder at the root of the archive. 
This helps reduce the amount of time Lambda spends extracting the deployment package when it creates a new container.

To execute the full build, including: compilation, tests, and packaging, you'll need to execute the build task. 

For Windows users:
```
gradlew build
```

For Mac and Linux users:
```
./gradlew build
```

Once the build has finished, upload the zip found in `build/distributions/` by clicking "Upload" in the "Function code" section.

## Configuring the Lambda Handler

The final step before testing the function is to configure the handler. A handler is a method that
is called when the Lambda function is triggered which should perform any endpoint specific transformations
before calling a method that does the actual computing. In this section of the workshop
all we are doing is returning a string, so the handler will do the formatting itself instead of calling a
separate method. However in later sections we will be separating the handler from the computing method.

1. The format for declaring a handler is `package.class::method` so in this case use `io.nuvalence.workshops.aws.lambda.HelloWorldHandler::handleRequest`. In JavaScript, the format is adding `module.exports = method` as the last line of your code, or in this case `module.exports = handleRequest`.
2. After setting the handler click "Save" in the top right to save all the changes we made.

## Testing Your Function
1. To begin testing the Lambda function select "Configure test events" from the dropdown to the left of "Test".
2. Delete the template, then in the event body enter:
    ```
    "<your name>"
    ``` 
    and click "Create". 
3. Next click "Test" and once it completes expand the "Details" section to see more information
about the test. 

This "Details" section contains some different metrics that can be helpful for development and configuring your Lambda function.
"Duration" is the amount of time that your Lambda function ran for. However, since Amazon charges for each 100ms, "Billed duration" 
will be the time that you actually pay for. Another important pair of metrics are "Memory Size" and "Max Memory Used", these are fairly self explanatory but "Memory Size"
is how much memory was allocated for the function, and "Max Memory Used" is the actual amount of memory that was used. 
If this call of the Lambda requires creating a new instance of the function then you'll also see "Init Duration", which is how much time was spent just creating the instance.   
