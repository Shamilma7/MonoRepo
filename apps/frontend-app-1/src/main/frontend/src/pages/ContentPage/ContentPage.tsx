import React, {useEffect, useState} from 'react';
import {Textarea} from "@navikt/ds-react";
import styled from "styled-components";
import BackendApp1Service from "~/service/BackendApp1Service";

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  gap: 20px;
  justify-content: center;
  align-items: center;
`

const ContentPage = () => {
    const [hello, setHello] = useState('');

    return (<Container>
      <button
      value="Kall"
                 onClick={e => BackendApp1Service.fetchHello().then(result => setHello(result))}
                >Kall på backend-app-1</button>
        <Textarea
            label=""
            value={hello}
            defaultValue={"Trykk på knappen!"}
        />
    </Container>)
};
export default ContentPage;