// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = '193.142.112.117:83' // 'dev.itti.com.pl:8009'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle, getHeadersFileDownload } from '../../../store/addons'
import fileDownload from 'react-file-download'
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
export const EXPORT_TO_CSV = 'EXPORT_TO_CSV'
export const SET_STATUS = 'SET_STATUS'
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

export const exportToCSVAction = (data = null) => {
  return {
    type: EXPORT_TO_CSV,
    data: data
  }
}

export const setStatusAction = (data = null) => {
  return {
    type: SET_STATUS,
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
  setStage,
  exportToCSV,
  setStatus
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
      axios.get(`http://${origin}/api/user?trialsession_id=${id}&size=1000`, getHeaders())
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
      axios.get(`http://${origin}/api/role?trial_id=${id}&size=1000`, getHeaders())
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
      axios.get(`http://${origin}/api/stages?trial_id=${id}&size=1000`, getHeaders())
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

export const exportToCSV = (id) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/answers/csv-file?trialsession_id=${id}`, getHeadersFileDownload())
        .then((response) => {
          toastr.success('Export to CSV', 'Export has been successful!', toastrOptions)
          fileDownload(response.data, 'summaryOfObservations.csv')
          resolve()
        })
        .catch((error) => {
          errorHandle(error.response.status)
          resolve()
        })
    })
  }
}

export const setStatus = (id, statusName) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.put(`http://${origin}/api/trialsessions/${id}/changeStatus?status=${statusName}`, {}, getHeaders())
          .then((response) => {
            dispatch(setStatusAction(response.data))
            toastr.success('Session settings', 'Status was changed!', toastrOptions)
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
  },
  [EXPORT_TO_CSV]: (state) => {
    return {
      ...state
    }
  },
  [SET_STATUS]: (state) => {
    return {
      ...state
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
