// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import {
  getHeaders,
  errorHandle
} from '../../../store/addons'

export const GET_TRIAL_DETAIL = 'GET_TRIAL_DETAIL'
export const UPDATE_TRIAL = 'UPDATE_TRIAL'
export const REMOVE_TRIAL = 'REMOVE_TRIAL'
// ------------------------------------
// Actions
// ------------------------------------
export const getTrialDetailAction = (data = null) => {
  return {
    type: GET_TRIAL_DETAIL,
    data: data
  }
}
export const updateTrialAction = (data = null) => {
  return {
    type: UPDATE_TRIAL,
    data: data
  }
}
export const removeTrialAction = (data = null) => {
  return {
    type: REMOVE_TRIAL,
    data: data
  }
}

export const getTrialDetail = id => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .get(`${origin}/api/admin/ostTrials?id=${id}`, getHeaders())
        .then(response => {
          dispatch(getTrialDetailAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    })
  }
}
export const updateTrial = trial => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .put(`${origin}/api/admin/updateTrial`, trial, getHeaders())
        .then(response => {
          dispatch(updateTrialAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    })
  }
}
export const removeTrial = id => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .delete(`${origin}/api/admin/deleteTrial?id=${id}`, getHeaders())
        .then(response => {
          dispatch(removeTrialAction(response.data))
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
  [GET_TRIAL_DETAIL]: (state, action) => {
    return {
      ...state,
      trialId: action.data.trialId,
      trialName: action.data.trialName,
      trialDescription: action.data.trialDescription,
      lastTrialStage: action.data.lastTrialStage,
      archived: action.data.archived,
      stageSet: action.data.stageSet,
      sessionSet: action.data.sessionSet,
      roleSet: action.data.roleSet
    }
  },
  [UPDATE_TRIAL]: (state, action) => {
    return {
      ...state,
      trialId: action.data.trialId,
      trialName: action.data.trialName,
      trialDescription: action.data.trialDescription,
      lastTrialStage: action.data.lastTrialStage,
      archived: action.data.archived
    }
  },
  [REMOVE_TRIAL]: (state, action) => {
    return {
      ...state,
      archived: action.data.archived
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  trialId: '',
  trialName: '',
  trialDescription: '',
  lastTrialStage: null,
  archived: false,
  stageSet: [],
  sessionSet: [],
  roleSet: []
}

export default function trialReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
