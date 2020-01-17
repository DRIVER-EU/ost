// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const ADD_NEW_QUESTION = 'ADD_NEW_QUESTION'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewQuestionAction = (data = null) => {
  return {
    type: ADD_NEW_QUESTION,
    data: data
  }
}

export const addNewQuestion = (question) => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(`${origin}/api/observationtypes/admin/addNewQuestionSet`, question, getHeaders())
        .then(response => {
          dispatch(addNewQuestionAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    })
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [ADD_NEW_QUESTION]: (state, action) => {
    return {
      ...state,
      newQuestionDetail: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  newQuestionDetail: {}
}

export default function newQuestionDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
