import { combineReducers } from 'redux'
import menuReducer from '../components/Menu/module/menu'
import { reducer as toastReducer } from 'react-redux-toastr'
import locationReducer from './location'
import loginReducer from '../routes/Login/modules/login'
import trialsReducer from './../routes/Trials/modules/trials'

export const makeRootReducer = (asyncReducers) => {
  return combineReducers({
    location: locationReducer,
    login: loginReducer,
    menu: menuReducer,
    toastr: toastReducer,
    trials: trialsReducer,
    ...asyncReducers
  })
}

export const injectReducer = (store, { key, reducer }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) return

  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
