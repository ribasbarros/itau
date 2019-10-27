import CommonResponse from './CommonResponse';
import GetErrorMessage from './GetErrorMessage';
import HandlePromise from './HandlePromise';
import API from './CreateRequest';

async function save(request) {
  const [err, response] = await HandlePromise(API.post(`/api/collect/v1`, request, {'Content-Type' : 'application/json'} ));
  if (err) {
    console.error(err);
    return CommonResponse({
      statusCode: err.errorCode || 500,
      errorMessage: GetErrorMessage(err)
    });
  }

  return CommonResponse({ response });
}

export default {
  save
};