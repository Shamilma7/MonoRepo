import { combineReducers, configureStore } from '@reduxjs/toolkit';


const rootReducer = combineReducers({});

const store = configureStore({
  reducer: rootReducer,
  middleware: (getDefaultMiddleware) => getDefaultMiddleware(),
});

export type RootState = ReturnType<typeof rootReducer>;

export type Dispatch = typeof store.dispatch;

export default store;
