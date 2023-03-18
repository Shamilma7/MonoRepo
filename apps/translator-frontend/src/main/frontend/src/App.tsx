import React, {useEffect} from 'react';
import './i18next';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {
    LoginPage
} from './pages';
import RussianToChechenTranslationService from "~/service/RussianToChechenTranslationService";


const Hei = () => {
    useEffect(() => {
        RussianToChechenTranslationService.fetchHei().then(value => {
                console.log("success")
                console.log(value)
            }
        )
        RussianToChechenTranslationService.fetchTranslation().then(value => {
                console.log("success")
                console.log(value)
            }
        )
    }, [])
    return <p>hei</p>
}
const App = () => (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Hei/>}/>
                <Route path="/logg-inn" element={<LoginPage/>}/>
            </Routes>
        </BrowserRouter>
    </>
);

export default App;