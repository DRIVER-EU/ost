// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_ACTIVE_SESSIONS = 'GET_ACTIVE_SESSIONS'
export const GET_SESSION_DETAIL = 'GET_SESSION_DETAIL'

// ------------------------------------
// Actions
// ------------------------------------
export const getActiveSessionsAction = (data = null) => {
  return {
    type: GET_ACTIVE_SESSIONS,
    data: data
  }
}

export const getSessionDetailAction = (data = null) => {
  return {
    type: GET_SESSION_DETAIL,
    data: data
  }
}
export const actions = {
  getActiveSessionsAction,
  getSessionDetailAction
}

export const getActiveSessions = () => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/trialsessions/admin/getListTrialSessionAllActive`,
          getHeaders()
        )
        .then(response => {
          dispatch(getActiveSessionsAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}
export const getSessionDetail = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/trialsessions/admin/getSessionUserWithStatistics?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(getSessionDetailAction(response.data))
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
  [GET_ACTIVE_SESSIONS]: (state, action) => {
    return {
      ...state,
      activeSessionsList: action.data
    }
  },
  [GET_SESSION_DETAIL]: (state, action) => {
    return {
      ...state,
      sessionDetail: action.data
    }
  }
}
  // ------------------------------------
  // Reducer
  // ------------------------------------
const initialState = {
  activeSessionsList: [],
  sessionDetail: []
}

export default function activeSessionsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
