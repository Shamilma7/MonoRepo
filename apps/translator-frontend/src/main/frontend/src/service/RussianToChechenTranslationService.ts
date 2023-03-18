import Api from '../api';


const fetchHei = () =>
    Api.get('/api/v1/hello', {
        headers: {Accept: 'text/plain'},
    }).then((response) => response.text());

const fetchTranslation = () =>
    Api.get('/api/russian-to-chechen-service/v1/translation', {
        headers: {Accept: 'text/plain'},
    }).then((response) => response.text());


export default {
    fetchHei,
    fetchTranslation
};