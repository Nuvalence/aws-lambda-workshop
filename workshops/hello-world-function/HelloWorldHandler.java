package io.nuvalence.workshops.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Lambda handler accepting a string and returns a message formatted "Hello ${input}.".
 */
public class HelloWorldHandler implements RequestHandler<String, String> {

    /**
     * Handles a lambda request by returning a message formatted "Hello ${input}".
     *
     * @param request input request
     * @param context injected lambda context
     * @return formatted message
     */
    public String handleRequest(String request, Context context) {
        return String.format("Hello %s.", request);
    }
}
