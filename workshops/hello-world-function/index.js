function helloWorldHandler(request, context) {
  return "Hello, " + request;
}

exports.handler = helloWorldHandler;
