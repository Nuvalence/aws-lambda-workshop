package io.nuvalence.workshops.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Lambda handler for a {@link FavoriteColorModel}.
 */
public class FavoriteColorRequestHandler implements RequestHandler<FavoriteColorModel, Void> {
    private final DynamoWriter writer;

    public FavoriteColorRequestHandler() {
        this(new DynamoWriter());
    }

    FavoriteColorRequestHandler(DynamoWriter dynamoWriter) {
        this.writer = dynamoWriter;
    }

    /**
     * Stores the request in the Dyanmo table.
     *
     * @param request favorite color request
     * @param context injected lambda context
     */
    public Void handleRequest(FavoriteColorModel request, Context context) {
        this.writer.writeToTable(request);
        return null;
    }
}