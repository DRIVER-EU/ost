// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
// if (origin === 'localhost' || origin === '192.168.1.15:8080') {
//   origin = '192.168.1.15:8080'
// } else {
origin = '192.168.1.15:8080'
// }
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_MESSAGES = 'GET_MESSAGES'
export const SEND_MESSAGE = 'SEND_MESSAGE'
export const GET_OBSERVATION = 'GET_OBSERVATION'
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

export const actions = {
  getMessages,
  sendMessage,
  getObservation
}

export const getMessages = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/anonymous/message`, getHeaders())
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
      axios.post(`http://${origin}/api/anonymous/message`, message, getHeaders())
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
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  messages: [],
  isSendMessage: {},
  observation: []
}

export default function adminTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
