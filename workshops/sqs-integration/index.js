const AWS = require("aws-sdk");
AWS.config.update({
  region: "REGION" //update this to reflect your region
});

const ddb = new AWS.DynamoDB({ apiVersion: "2012-08-10" });

function handleRequest(event, context) {
  event.Records.forEach(record => {
    const body = JSON.parse(record.body);
    let ddbParams = {
      TableName: "table-name", //update this to reflect your table's name
      Item: {
        name: { S: body.name },
        color: { S: body.color },
        date: { N: String(Date.now()) }
      }
    };

    console.log(ddbParams);

    ddb.putItem(ddbParams, function(err, res) {
      if (err) {
        console.log("Error", err);
      } else {
        console.log("Success", res);
      }
    });
  });

  return;
}

exports.handler = handleRequest;
