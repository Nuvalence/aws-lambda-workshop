package io.nuvalence.workshops.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Handles favorite color requests on an SQS Queue.
 */
public class SqsMessageHandler implements RequestHandler<SQSEvent, Void> {
    private final DynamoWriter writer;
    private final ObjectMapper mapper = new ObjectMapper();

    public SqsMessageHandler() {
        this(new DynamoWriter());
    }

    SqsMessageHandler(DynamoWriter dynamoWriter) {
        this.writer = dynamoWriter;
    }

    /**
     * Handles favorite color requests from an SQS queue.
     *
     * @param input   SQS event containing one or many requests
     * @param context injected lambda context
     */
    public Void handleRequest(SQSEvent input, Context context) {
        System.out.println(input);
        try {
            for (SQSEvent.SQSMessage message : input.getRecords()) {
                FavoriteColorModel data = this.mapper.readValue(message.getBody(), FavoriteColorModel.class);
                this.writer.writeToTable(data);
            }
        } catch (IOException e) {
            System.err.println("Encountered an error: " + e.getMessage());
        }
        return null;
    }
}
