// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const ADD_NEW_QUESTION_DETAIL = 'ADD_NEW_QUESTION_DETAIL'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewQuestionAction = (data = null) => {
  return {
    type: ADD_NEW_QUESTION_DETAIL,
    data: data
  }
}

export const addNewQuestionDetail = (question) => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(`${origin}/api/questions/admin/addNewQuestion`, question, getHeaders())
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
  [ADD_NEW_QUESTION_DETAIL]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      questionName: action.data.name,
      description: action.data.description,
      position: action.data.position,
      answerType: action.data.answerType,
      questions: action.data.questions,
      commented: action.data.commented
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  id: 0,
  questionName: '',
  option: [],
  required: false,
  commented: false
}

export default function newQuestionReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
