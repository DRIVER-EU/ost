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

export const GET_MESSAGES = 'GET_MESSAGES'
export const SEND_MESSAGE = 'SEND_MESSAGE'
export const GET_OBSERVATION = 'GET_OBSERVATION'
export const GET_USERS = 'GET_USERS'
export const GET_ROLES = 'GET_ROLES'
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

export const actions = {
  getMessages,
  sendMessage,
  getObservation,
  getUsers,
  getRoles
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

export const getObservation = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/anonymous/observation`, getHeaders())
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
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  messages: [],
  isSendMessage: {},
  observation: [],
  usersList: [],
  rolesList: []
}

export default function adminTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
