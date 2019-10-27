export default function HandlePromise(promise) {
  return promise.then(data => [null, data]).catch(err => [err]);
}