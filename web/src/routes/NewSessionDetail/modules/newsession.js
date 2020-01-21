// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const ADD_NEW_SESSION = 'ADD_NEW_SESSION'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewSessionAction = (data = null) => {
  return {
    type: ADD_NEW_SESSION,
    data: data
  }
}

export const addNewSession = session => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(
          `${origin}/api/trialsessions/admin/addNewTrialSession`,
          session,
          getHeaders()
        )
        .then(response => {
          dispatch(addNewSessionAction(response.data))
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
  [ADD_NEW_SESSION]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      sessionName: action.data.name,
      status: action.data.status,
      stageId: action.data.lastTrialStageId,
      stageName: action.data.lastTrialStageName,
      stages: action.data.stages,
      userRoles: action.data.userRoles
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  id: '',
  sessionName: '',
  status: 'ACTIVE',
  stageId: 1,
  stageName: '',
  stages: [],
  userRoles: []
}

export default function newSessionDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
