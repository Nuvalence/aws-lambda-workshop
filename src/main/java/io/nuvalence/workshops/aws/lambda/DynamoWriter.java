package io.nuvalence.workshops.aws.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;

/**
 * Responsible for writing to Dynamo.
 */
public class DynamoWriter {
    private final AmazonDynamoDB dynamo;
    private final String tableName;

    public DynamoWriter() {
        //Use environment variable to configure table name
        this(AmazonDynamoDBClientBuilder.defaultClient(), System.getenv("TABLE_NAME"));
    }

    DynamoWriter(AmazonDynamoDB dynamoConnection, String table) {
        this.dynamo = dynamoConnection;
        this.tableName = table;
    }

    public void writeToTable(FavoriteColorModel data) {
        //Create item to put in DynamoDB
        HashMap<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        long timestamp = System.currentTimeMillis();

        //Insert values into item
        item.put("name", new AttributeValue(data.getName()));
        item.put("date", new AttributeValue().withN(Long.toString(timestamp)));
        item.put("color", new AttributeValue(data.getColor()));

        //Put item in DynamoDB table
        System.out.println("Adding new item...");
        this.dynamo.putItem(this.tableName, item);
        System.out.println("Success!");
    }
}