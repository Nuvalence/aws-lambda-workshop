package io.nuvalence.workshops.aws.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.Map;
import java.util.UUID;

public class DynamoWriterTest {
    private final AmazonDynamoDB mockClient = Mockito.mock(AmazonDynamoDB.class);
    private final String tableName = "TABLE_" + UUID.randomUUID().toString();
    private final DynamoWriter writer = new DynamoWriter(mockClient, tableName);

    @Test
    public void writeToTable_GivenRequest_ShouldWriteToDynamoTable() {
        FavoriteColorModel data = new FavoriteColorModel();
        data.setName("user " + UUID.randomUUID().toString());
        data.setColor("red");

        writer.writeToTable(data);

        ArgumentMatcher<Map<String, AttributeValue>> matchesInput = argument -> {
            AttributeValue actualName = argument.get("name");
            boolean matchesName = actualName != null && data.getName().equals(actualName.getS());
            AttributeValue actualColor = argument.get("color");
            boolean matchesColor = actualColor != null && data.getColor().equals(actualColor.getS());
            return matchesName && matchesColor;
        };

        Mockito.verify(mockClient).putItem(Mockito.eq(tableName), Mockito.argThat(matchesInput));
    }
}
