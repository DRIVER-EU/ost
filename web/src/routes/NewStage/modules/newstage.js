// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const ADD_NEW_STAGE = 'ADD_NEW_STAGE'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewStageAction = (data = null) => {
  return {
    type: ADD_NEW_STAGE,
    data: data
  }
}

export const addNewStage = (stage) => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(`${origin}/api/stages/admin/addNewTrialStage`, stage, getHeaders())
        .then(response => {
          dispatch(addNewStageAction(response.data))
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
  [ADD_NEW_STAGE]: (state, action) => {
    return {
      ...state,
      newStageDetail: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  newStageDetail: {}
}

export default function newStageDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
