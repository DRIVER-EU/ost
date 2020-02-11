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
export const GET_USERS_LIST = 'GET_USERS_LIST'
export const ADD_USER = 'ADD_USER'
export const REMOVE_USER = 'REMOVE_USER'

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
export const getUsersListAction = (data = null) => {
  return {
    type: GET_USERS_LIST,
    data: data
  }
}
export const addUserAction = (data = null) => {
  return {
    type: ADD_USER,
    data: data
  }
}
export const removeUserAction = () => {
  return {
    type: REMOVE_USER
  }
}

export const actions = {
  getSessionDetailAction,
  updateSessionAction,
  removeSessionAction,
  getUsersListAction,
  removeUserAction,
  addUserAction
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
      axios
        .get(
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

export const getUsersList = () => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(
          `${origin}/api/auth/users?page=0&size=10&sort=login,asc&sort=lastName,desc`,
          getHeaders()
        )
        .then(response => {
          dispatch(getUsersListAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}
export const addUser = user => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .post(
          `${origin}/api/trialsessions/admin/addNewUserRoleSession`,
          user,
          getHeaders()
        )
        .then(response => {
          dispatch(addUserAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const removeUser = (trialRoleId, trialSessionId, trialUserId) => {
  let path = '/api/trialsessions/admin/deleteUserRoleSession'
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}${path}?trialRoleId=${trialRoleId}&trialSessionId=${trialSessionId}&trialUserId=${trialUserId}`,
          getHeaders()
        )
        .then(response => {
          dispatch(removeUserAction(response.data))
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
      userRoles: action.data.userRoles,
      manual: action.data.manualStageChange
    }
  },
  [UPDATE_SESSION]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      sessionName: action.data.name,
      status: action.data.status,
      manual: action.data.manualStageChange,
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
  },
  [GET_USERS_LIST]: (state, action) => {
    return {
      ...state,
      usersList: action.data.data
    }
  },
  [ADD_USER]: (state, action) => {
    return {
      ...state,
      user: action.data
    }
  },
  [REMOVE_USER]: (state, action) => {
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
  sessionName: '',
  status: 'ACTIVE',
  stageId: 1,
  stageName: '',
  stages: [],
  userRoles: [],
  openRemoveInfoDialog: false,
  usersList: [],
  user: {},
  manual: false
}

export default function sessionDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
