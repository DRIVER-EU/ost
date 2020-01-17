// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_QUESTION = 'GET_QUESTION'
export const UPDATE_QUESTION = 'UPDATE_QUESTION'
export const REMOVE_QUESTION = 'REMOVE_QUESTION'
// ------------------------------------
// Actions
// ------------------------------------
export const getQuestionDetailAction = (data = null) => {
  return {
    type: GET_QUESTION,
    data: data
  }
}

export const updateQuestionAction = (data = null) => {
  return {
    type: UPDATE_QUESTION,
    data: data
  }
}

export const removeQuestionAction = (data = null) => {
  return {
    type: REMOVE_QUESTION,
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
          `${origin}/api/observationtypes/admin/updateQuestionSet`, stage,
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

export const removeQuestion = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}/api/observationtypes/admin/deleteQuestionSet?id=${id}`,
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

export const getQuestion = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/observationtypes/admin/getFullQuestionSet?id=${id}`,
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
  [GET_QUESTION]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      questionName: action.data.name,
      description: action.data.description,
      position: action.data.position,
      questions: action.data.questions
    }
  },
  [UPDATE_QUESTION]: (state, action) => {
    return {
      ...state,
      questionName: action.data.name,
      id: action.data.id,
      description: action.data.description,
      position: action.data.position
    }
  },
  [REMOVE_QUESTION]: (state, action) => {
    return {
      ...state
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  stageId: 0,
  stageName: '',
  questions: []
}

export default function questionDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
