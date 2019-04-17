// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = '192.168.1.151:8080'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_SCHEMA = 'GET_SCHEMA'
export const SEND_OBSERVATION = 'SEND_OBSERVATION'
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

export const actions = {
  getSchemaAction,
  sendObservation
}

export const getSchemaView = (idObs) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      /* eslint-disable */
      axios.get(`http://${origin}/api/questions-answers?answer_id=${idObs}`, getHeaders())
      /* eslint-enable */
          .then((response) => {
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
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const sendObservation = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.post(`http://${origin}/api/anonymous/observation`, getHeaders())
          .then((response) => {
            dispatch(sendObservationAction(response.data))
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
