// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import {
  getHeaders,
  errorHandle
} from '../../../store/addons'

export const ADD_NEW_TRIAL = 'ADD_NEW_TRIAL'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewTrialAction = (data = null) => {
  return {
    type: ADD_NEW_TRIAL,
    data: data
  }
}

export const addNewTrial = (trial) => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(`${origin}/api/admin/addNewTrial`, trial, getHeaders())
        .then(response => {
          dispatch(addNewTrialAction(response.data))
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
  [ADD_NEW_TRIAL]: (state, action) => {
    return {
      ...state,
      newTrialDetail: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  newTrialDetail: {}
}

export default function newTrialReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
