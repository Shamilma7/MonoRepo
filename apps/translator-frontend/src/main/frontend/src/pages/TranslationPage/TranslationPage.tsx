import React, {useEffect, useState} from 'react';
import {Textarea} from "@navikt/ds-react";
import styled from "styled-components";
import RussianToChechenTranslationService from "~/service/RussianToChechenTranslationService";

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  gap: 20px;
  justify-content: center;
  align-items: center;
`

const TranslationPage = () => {
    const [inputText, setInputText] = useState('');
    const [translation, setTranslation] = useState('');

    useEffect(() => {
        RussianToChechenTranslationService.fetchTranslation(
            {
                body: {
                    input: inputText
                }
            }
        ).then(result => setTranslation(result))
    }, [inputText])


    return (<Container>
        <Textarea
            label="Russisk"
            value={inputText}
            defaultValue={"Skriv russisk her"}
            onChange={event => setInputText(event.target.value)}
        />

        <Textarea
            label="Tsjetsjensk"
            value={translation}
        />
    </Container>)
};
export default TranslationPage;