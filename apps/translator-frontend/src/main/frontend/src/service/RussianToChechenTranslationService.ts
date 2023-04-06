import Api from '../api';


export type TranslationRequest = {
    input: string
}

export type TranslationResponse = {
    result: string
}

const fetchHei = () =>
    Api.get('/api/v1/hello', {
        headers: {Accept: 'text/plain'},
    }).then((response) => response.text());

const fetchTranslation = ({body}: { body: TranslationRequest }) =>
    Api.postJson<TranslationResponse>(
        '/api/russian-to-chechen-service/v1/translation',
        {headers: {Accept: 'text/plain'}},
        JSON.stringify(body)
    ).then((response) => response.result);


export default {
    fetchHei,
    fetchTranslation
};