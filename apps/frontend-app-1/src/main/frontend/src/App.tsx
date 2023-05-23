import React from 'react';
import './i18next';

import LoginPage from "~/pages/LoginPage";
import ContentPage from "~/pages/ContentPage";
import {BrowserRouter, Route, Routes} from 'react-router-dom';


const App = () => (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<ContentPage/>}/>
                <Route path="/logg-inn" element={<LoginPage/>}/>
            </Routes>
        </BrowserRouter>
    </>
);

export default App;