// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_STAGE = 'GET_STAGE'
export const UPDATE_STAGE = 'UPDATE_STAGE'
export const REMOVE_STAGE = 'REMOVE_STAGE'
// ------------------------------------
// Actions
// ------------------------------------
export const getStageDetailAction = (data = null) => {
  return {
    type: GET_STAGE,
    data: data
  }
}

export const updateStageAction = (data = null) => {
  return {
    type: UPDATE_STAGE,
    data: data
  }
}

export const removeStageAction = (data = null) => {
  return {
    type: REMOVE_STAGE,
    data: data
  }
}

export const actions = {
  getStageDetailAction,
  updateStageAction,
  removeStageAction
}

export const updateStage = stage => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .put(
          `${origin}/api/stages/admin/updateTrialStage`, stage,
          getHeaders()
        )
        .then(response => {
          dispatch(updateStageAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const removeStage = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}/api/stages/admin/deleteTrialStage?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(removeStageAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const getStageById = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/stages/admin/trialStageWithQuestions?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(getStageDetailAction(response.data))
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
  [GET_STAGE]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      stageName: action.data.name,
      questions: action.data.questions
    }
  },
  [UPDATE_STAGE]: (state, action) => {
    return {
      ...state,
      stageName: action.data.name,
      id: action.data.id
    }
  },
  [REMOVE_STAGE]: (state, action) => {
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

export default function stageDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
