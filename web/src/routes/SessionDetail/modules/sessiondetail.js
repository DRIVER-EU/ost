// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_SESSION = 'GET_SESSION'
export const UPDATE_SESSION = 'UPDATE_SESSION'
export const REMOVE_SESSION = 'REMOVE_SESSION'
export const REMOVE_INFO = 'REMOVE_INFO'
// ------------------------------------
// Actions
// ------------------------------------
export const getSessionDetailAction = (data = null) => {
  return {
    type: GET_SESSION,
    data: data
  }
}

export const updateSessionAction = (data = null) => {
  return {
    type: UPDATE_SESSION,
    data: data
  }
}

export const removeSessionAction = (data = null) => {
  return {
    type: REMOVE_SESSION,
    data: data
  }
}
export const getResponseError = () => {
  return {
    type: REMOVE_INFO
  }
}

export const actions = {
  getSessionDetailAction,
  updateSessionAction,
  removeSessionAction
}

export const updateSession = session => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .put(
          `${origin}/api/trialsessions/admin/updateTrialSession`,
          session,
          getHeaders()
        )
        .then(response => {
          dispatch(updateSessionAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const removeSession = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}/api/trialsessions/admin/deleteTrialSession?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(removeSessionAction(response.data))
          resolve()
        })
        .catch(error => {
          dispatch(getResponseError())
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const getSessionById = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/trialsessions/admin/getFullTrialSession?id=${id}`,
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

const FileDownload = require('js-file-download')
export const getObservations = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios.get(
        `${origin}/api/answers/csv-file?trialsession_id=${id}`,
        getHeaders()
      )
      .then(response => {
        FileDownload(response.data, 'observations.csv')
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
  [GET_SESSION]: (state, action) => {
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
  },
  [UPDATE_SESSION]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      sessionName: action.data.name,
      status: action.data.status,
      stageId: action.data.lastTrialStageId
    }
  },
  [REMOVE_SESSION]: (state, action) => {
    return {
      ...state
    }
  },
  [REMOVE_INFO]: (state, action) => {
    return {
      ...state,
      openRemoveInfoDialog: true
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  id: 0,
  sessionName: '',
  status: 'ACTIVE',
  stageId: 1,
  stageName: '',
  stages: [],
  userRoles: [],
  openRemoveInfoDialog: false
}

export default function sessionDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
