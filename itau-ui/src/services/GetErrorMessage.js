const GetErrorMessage = (err, message = '') => {
    if (err) {
      return err.errorMessage || err.message || err.faultstring || err.errorDescription || message;
    }
  
    return message;
  };
  
  export default GetErrorMessage;