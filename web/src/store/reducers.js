import { combineReducers } from 'redux'
import menuReducer from '../components/Menu/module/menu'
import { reducer as toastReducer } from 'react-redux-toastr'
import locationReducer from './location'
import loginReducer from '../routes/Login/modules/login'
import newObservationReducer from '../routes/NewObservation/modules/newobservation'
import Question from '../routes/Question/modules/question'
import adminTrialsReducer from '../routes/AdminTrials/modules/admin_trials'
import viewTrialsReducer from '../routes/ViewTrials/modules/view_trials'
import stageDetailReducer from '../routes/StageDetail/modules/stagedetail'
import layoutReducer from '../layouts/CoreLayout/layout-reducer'
import trialReducer from '../routes/TrialDetail/modules/trialdetail'
import newTrialReducer from '../routes/NewTrial/modules/newtrial'
import newStageDetailReducer from '../routes/NewStage/modules/newstage'
import newQuestionDetailReducer from '../routes/NewQuestionSet/modules/newquestion'
import questionDetailReducer from '../routes/QuestionSet/modules/questiondetail'
import newQuestionReducer from '../routes/NewQuestion/modules/newquestion'
import questionReducer from '../routes/QuestionDetail/modules/question'
import sessionDetailReducer from '../routes/SessionDetail/modules/sessiondetail'
import roleDetailReducer from '../routes/RoleDetail/modules/roledetail'
import newRoleDetailReducer from '../routes/NewRole/modules/newrole'
import usersManagerReducer from '../routes/Users/modules/users'

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
    layout: layoutReducer,
    stageDetail: stageDetailReducer,
    trialDetail: trialReducer,
    newTrialDetail: newTrialReducer,
    newStageDetail: newStageDetailReducer,
    newQuestion: newQuestionDetailReducer,
    questionSet: questionDetailReducer,
    newQuestionDetail: newQuestionReducer,
    questionDetail: questionReducer,
    sessionDetail: sessionDetailReducer,
    roleDetail: roleDetailReducer,
    newRole: newRoleDetailReducer,
    usersManager: usersManagerReducer,
    ...asyncReducers
  })
}

export const injectReducer = (store, { key, reducer }) => {
  if (Object.hasOwnProperty.call(store.asyncReducers, key)) return

  store.asyncReducers[key] = reducer
  store.replaceReducer(makeRootReducer(store.asyncReducers))
}

export default makeRootReducer
