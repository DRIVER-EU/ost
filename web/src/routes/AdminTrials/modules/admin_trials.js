// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = 'dev.itti.com.pl:8009'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const GET_MESSAGES = 'GET_MESSAGES'
export const SEND_MESSAGE = 'SEND_MESSAGE'
export const GET_OBSERVATION = 'GET_OBSERVATION'
export const GET_USERS = 'GET_USERS'
export const GET_ROLES = 'GET_ROLES'
export const GET_STAGES = 'GET_STAGES'
export const SET_STAGE = 'SET_STAGE'
// ------------------------------------
// Actions
// ------------------------------------

export const getMessagesAction = (data = null) => {
  return {
    type: GET_MESSAGES,
    data: data
  }
}

export const sendMessageAction = (data = null) => {
  return {
    type: SEND_MESSAGE,
    data: data
  }
}

export const getObservationAction = (data = null) => {
  return {
    type: GET_OBSERVATION,
    data: data
  }
}

export const getUsersAction = (data = null) => {
  return {
    type: GET_USERS,
    data: data
  }
}

export const getRolesAction = (data = null) => {
  return {
    type: GET_ROLES,
    data: data
  }
}

export const getStagesAction = (data = null) => {
  return {
    type: GET_STAGES,
    data: data
  }
}

export const setStageAction = (data = null) => {
  return {
    type: SET_STAGE,
    data: data
  }
}

export const actions = {
  getMessages,
  sendMessage,
  getObservation,
  getUsers,
  getRoles,
  getStages,
  setStage
}

export const getMessages = (id, sort = '') => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/event/search?trialsession_id=${id}&sort=${sort.type},${sort.order}`, getHeaders())
       .then((response) => {
         dispatch(getMessagesAction(response.data))
         resolve()
       })
       .catch((error) => {
         errorHandle(error)
         resolve()
       })
    })
  }
}

export const sendMessage = (message) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.post(`http://${origin}/api/event`, message, getHeaders())
         .then((response) => {
           dispatch(sendMessageAction(message))
           resolve()
         })
         .catch((error) => {
           errorHandle(error.response.status)
           resolve()
         })
    })
  }
}

export const getObservation = (id, search) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/answers?trialsession_id=${id}&search=${search}`, getHeaders())
          .then((response) => {
            dispatch(getObservationAction(response.data))
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const getUsers = (id) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/user?trialsession_id=${id}`, getHeaders())
          .then((response) => {
            dispatch(getUsersAction(response.data))
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const getRoles = (id) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/role?trialsession_id=${id}`, getHeaders())
          .then((response) => {
            dispatch(getRolesAction(response.data))
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const getStages = (id) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/stages?trialsession_id=${id}`, getHeaders())
          .then((response) => {
            dispatch(getStagesAction(response.data))
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const setStage = (id, stageId) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.put(`http://${origin}/api/trialsessions/${id}`, stageId, getHeaders())
          .then((response) => {
            dispatch(setStageAction(response.data))
            toastr.success('Session settings', 'Stage was selected!', toastrOptions)
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            toastr.error('Session settings', 'Error!', toastrOptions)
            resolve()
          })
    })
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_MESSAGES]: (state, action) => {
    return {
      ...state,
      messages: action.data
    }
  },
  [SEND_MESSAGE]: (state, action) => {
    return {
      ...state,
      isSendMessage: action.data
    }
  },
  [GET_OBSERVATION]: (state, action) => {
    return {
      ...state,
      observation: action.data
    }
  },
  [GET_USERS]: (state, action) => {
    return {
      ...state,
      usersList: action.data
    }
  },
  [GET_ROLES]: (state, action) => {
    return {
      ...state,
      rolesList: action.data
    }
  },
  [GET_STAGES]: (state, action) => {
    return {
      ...state,
      stagesList: action.data
    }
  },
  [SET_STAGE]: (state, action) => {
    return {
      ...state,
      stageActive: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  messages: {},
  isSendMessage: {},
  observation: [],
  usersList: {},
  rolesList: {},
  stagesList: {},
  stageActive: {}
}

export default function adminTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
