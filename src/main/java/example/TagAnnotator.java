package example;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.ListTagsRequest;
import com.amazonaws.services.lambda.model.ListTagsResult;
import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.xray.AWSXRay;
//import com.amazonaws.xray.entities.Subsegment;

import java.util.Map;

public class TagAnnotator {
    /**
     * Adds the tags on a Lambda function to an x-ray trace as annotations.
     *
     * @param context Context object from Lambda function to annotate
     */
    public static void annotateWithTagsFromResource(Context context) {
        //Subsegment subsegment = AWSXRay.beginSubsegment("Annotating tags");
        AWSLambda client = AWSLambdaClientBuilder.standard().build();
        ListTagsRequest request = new ListTagsRequest().withResource(context.getInvokedFunctionArn());
        ListTagsResult results = client.listTags(request);
        Map<String, String> tags = results.getTags();
        //for (Map.Entry<String,String> tag : tags.entrySet())
            //subsegment.putAnnotation(tag.getKey(), tag.getValue());
        System.out.println(results);
        //AWSXRay.endSubsegment();
    }
}
