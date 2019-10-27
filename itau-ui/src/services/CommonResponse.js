export default function CommonResponse({
    statusCode = 200,
    errorMessage = '',
    response = {}
  }) {
    return Promise.resolve({
      statusCode,
      errorMessage,
      response
    });
  }