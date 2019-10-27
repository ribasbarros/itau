import axios from 'axios';

const request = async (method, path, payload, headers = {}) => {

  const axiosInstance = axios.create({
    baseURL: window.localStorage.getItem('apiUrl'),
    headers: {
      'Content-Type' : 'application/json', 
      'accept' : 'application/json'
    }
  });

  let optionsHeaders = { ...headers };

  const options = {
    method,
    headers: optionsHeaders,
    url: path
  };

  if (payload) {
    if (payload.params) {
      options.params = payload.params;
    } else {
      options.data = payload;
    }
  }

  try {
    const { data } = await axiosInstance.request(options);
    return data;
  } catch (error) {
    if (error && error.response) {
      return Promise.reject(error.response.data);
    }
    if (error && error.request) {
      return Promise.reject();
    }

    return Promise.reject();
  }
};

const get = (path, payload, headers, withCredentials, auth) => request('GET', path, payload, headers, withCredentials, auth);
const post = (path, payload, headers, withCredentials, auth) => request('POST', path, payload, headers, withCredentials, auth);
const put = (path, payload, headers, withCredentials, auth) => request('PUT', path, payload, headers, withCredentials, auth);
const deleteMethod = (path, payload, headers, withCredentials, auth) => request('DELETE', path, payload, headers, withCredentials, auth);

export default {
  get,
  post,
  put,
  delete: deleteMethod
};