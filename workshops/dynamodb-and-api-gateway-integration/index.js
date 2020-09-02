const AWS = require("aws-sdk");
AWS.config.update({
  region: "REGION" //update this to reflect your region
});

const ddb = new AWS.DynamoDB({ apiVersion: "2012-08-10" });

function handleRequest(request, context) {
  var params = {
    TableName: "table-name", //update this to reflect your table name
    Item: {
      name: { S: request.name },
      color: { S: request.color },
      date: { N: String(Date.now()) }
    }
  };

  ddb.putItem(params, function(err, data) {
    if (err) {
      console.log("Error", err);
    } else {
      console.log("Success", data);
    }
  });

  return;
}

exports.handler = handleRequest;
