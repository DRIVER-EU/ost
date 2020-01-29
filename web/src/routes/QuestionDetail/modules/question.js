// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_QUESTION_DETAIL = 'GET_QUESTION_DETAIL'
export const UPDATE_QUESTION_DETAIL = 'UPDATE_QUESTION_DETAIL'
export const REMOVE_QUESTION_DETAIL = 'REMOVE_QUESTION_DETAIL'
// ------------------------------------
// Actions
// ------------------------------------
export const getQuestionDetailAction = (data = null) => {
  return {
    type: GET_QUESTION_DETAIL,
    data: data
  }
}

export const updateQuestionAction = (data = null) => {
  return {
    type: UPDATE_QUESTION_DETAIL,
    data: data
  }
}

export const removeQuestionAction = (data = null) => {
  return {
    type: REMOVE_QUESTION_DETAIL,
    data: data
  }
}

export const actions = {
  getQuestionDetailAction,
  updateQuestionAction,
  removeQuestionAction
}

export const updateQuestion = stage => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .put(
          `${origin}/api/questions/admin/updateQuestion`, stage,
          getHeaders()
        )
        .then(response => {
          dispatch(updateQuestionAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const removeQuestionDetail = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}/api/questions/admin/deleteQuestion?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(removeQuestionAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const getQuestionDetail = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/questions/admin/getFullQuestion?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(getQuestionDetailAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_QUESTION_DETAIL]: (state, action) => {
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
  },
  [UPDATE_QUESTION_DETAIL]: (state, action) => {
    return {
      ...state,
      questionName: action.data.name,
      id: action.data.id,
      description: action.data.description,
      position: action.data.position,
      commented: action.data.commented,
      answerType: action.data.answerType
    }
  },
  [REMOVE_QUESTION_DETAIL]: (state, action) => {
    return {
      ...state
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

export default function questionReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
