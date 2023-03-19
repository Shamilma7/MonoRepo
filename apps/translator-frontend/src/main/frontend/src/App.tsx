import React from 'react';
import './i18next';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import TranslationPage from "~/pages/TranslationPage";


const App = () => (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<TranslationPage/>}/>
                <Route path="/logg-inn" element={<TranslationPage/>}/>
            </Routes>
        </BrowserRouter>
    </>
);

export default App;