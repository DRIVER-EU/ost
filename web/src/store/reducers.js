import { combineReducers } from 'redux'
import menuReducer from '../components/Menu/module/menu'
import { reducer as toastReducer } from 'react-redux-toastr'
import locationReducer from './location'
import loginReducer from '../routes/Login/modules/login'
import newObservationReducer from '../routes/NewObservation/modules/newobservation'
import Question from '../routes/Question/modules/question'
import adminTrialsReducer from '../routes/AdminTrials/modules/admin_trials'
import viewTrialsReducer from '../routes/ViewTrials/modules/view_trials'

export const makeRootReducer = (asyncReducers) => {
  return combineReducers({
    location: locationReducer,
    login: loginReducer,
    menu: menuReducer,
    toastr: toastReducer,
    newobservation: newObservationReducer,
    question: Question,
    adminTrials: adminTrialsReducer,
    viewTrials: viewTrialsReducer,
    ...asyncReducers
  })
}

export const injectReducer = (store, { key, reducer }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) return

  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
