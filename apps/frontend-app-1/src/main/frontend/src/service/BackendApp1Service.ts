import Api from '../api';

const fetchHello = () =>
    Api.get('/api/backend-app-1-service/v1/hello', {
        headers: {Accept: 'text/plain'},
    }).then((response) => response.text());

/*
const fetchJson = ({body}: { body: RequestDto }) =>
    Api.getJson<ResponseDto>(
        '/api/backend-app-1-service/v1/hello',
        {headers: {Accept: 'application/json'}},
        JSON.stringify(body)
    ).then((response) => response.result);
*/

export default {
    fetchHello,
    // fetchJson
};