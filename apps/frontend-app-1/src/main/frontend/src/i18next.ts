import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

import nb from './locales/nb/translation.json';
import nn from './locales/nn/translation.json';

const isDebugging = false;

i18next
  .use(initReactI18next)
  .use(LanguageDetector)
  .init({
    debug: isDebugging,
    fallbackLng: 'nb',
    lng: 'nb',
    resources: {
      nb: nb,
      nn: nn,
    },
  });
