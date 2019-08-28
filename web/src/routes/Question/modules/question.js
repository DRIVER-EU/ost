// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = 'testbed-ost.itti.com.pl'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle, freeQueue } from '../../../store/addons'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const GET_SCHEMA = 'GET_SCHEMA'
export const SEND_OBSERVATION = 'SEND_OBSERVATION'
export const RESET_OBSERVATION_QUESTION = 'RESET_OBSERVATION_QUESTION'
// ------------------------------------
// Actions
// ------------------------------------

export const getSchemaAction = (data = null) => {
  return {
    type: GET_SCHEMA,
    data: data
  }
}

export const sendObservationAction = (data = null) => {
  return {
    type: SEND_OBSERVATION,
    data: data
  }
}

export const resetObservationAction = (data = null) => {
  return {
    type: RESET_OBSERVATION_QUESTION,
    data: data
  }
}

export const actions = {
  getSchemaAction,
  sendObservation,
  resetObservation
}

// backend errors: The requested resource cannot be converted to DTO
// leaving it as buggy as it is
export const getSchemaView = (idObs) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`https://${origin}/api/questions-answers?answer_id=${idObs}`, getHeaders())
          .then((response) => {
            freeQueue()
            window.indexedDB.open('driver', 1).onsuccess = (event) => {
              let store = event.target.result.transaction(['observation_answer'],
                'readwrite').objectStore('observation_answer')
              store.get(response.data.answerId).onsuccess = (x) => {
                if (!x.target.result) {
                  let d = parseInt(idObs)
                  store.add(Object.assign(response.data,
                    { answerId: d, id: d }))
                } else {
                  store.delete(response.data.answerId).onsuccess = () => {
                    let d = parseInt(idObs)
                    store.add(Object.assign(response.data,
                      { answerId: d, id: d }))
                  }
                }
              }
            }
            let change = {
              name: response.data.name,
              description: response.data.description,
              jsonSchema: { ...response.data.questionSchema, formData: response.data.formData },
              attachments: response.data.attachments,
              trialRoles: response.data.trialRoles,
              time: response.data.time,
              trialTime: response.data.trialTime
            }
            dispatch(getSchemaAction(change))
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              window.indexedDB.open('driver', 1).onsuccess = (event) => {
                event.target.result.transaction(['observation_answer'],
                'readonly').objectStore('observation_answer').index('answerId')
                .get(parseInt(idObs)).onsuccess = (e) => {
                  let change = {
                    name: e.target.result.name,
                    description: e.target.result.description,
                    jsonSchema: { ...e.target.result.questionSchema, formData: e.target.result.formData },
                    attachments: e.target.result.attachments,
                    trialRoles: e.target.result.trialRoles,
                    time: e.target.result.time,
                    trialTime: e.target.result.trialTime
                  }
                  dispatch(getSchemaAction(change))
                }
              }
            }
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const sendObservation = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.post(`https://${origin}/api/anonymous/observation`, getHeaders())
          .then((response) => {
            dispatch(sendObservationAction(response.data))
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              toastr.warning('Offline mode', 'Message will be send later', toastrOptions)
              if (localStorage.getItem('online')) { localStorage.removeItem('online') }
              window.indexedDB.open('driver', 1).onsuccess = event => {
                event.target.result.transaction(['sendQueue'], 'readwrite').objectStore('sendQueue').add({
                  type: 'post',
                  address: `https://${origin}/api/anonymous/observation`,
                  data: {}
                })
              }
            }
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const resetObservation = () => {
  return (dispatch) => {
    dispatch(resetObservationAction({}))
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_SCHEMA]: (state, action) => {
    return {
      ...state,
      observationForm: action.data
    }
  },
  [SEND_OBSERVATION]: (state, action) => {
    return {
      ...state,
      observation: action.data
    }
  },
  [RESET_OBSERVATION_QUESTION]: (state, action) => {
    return {
      ...state,
      observationForm: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  observationForm: {},
  observation: {}
}

export default function newobservationReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
