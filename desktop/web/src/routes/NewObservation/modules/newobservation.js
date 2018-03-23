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
  getSchema,
  sendObservation
}

export const getSchema = () => {
  return (dispatch) => {
    dispatch(getSchemaAction({
      schema: {
        'type': 'object',
        'properties': {
          'info': {
            'type': 'string',
            'title': 'How accurate was the sharing of information?'
          },
          'slider': {
            'title': 'How long id this information sharing take?',
            'type': 'integer',
            'min': 1,
            'max':10,
            'value': 2,
            'step': 1
          }
        }
      },
      uiSchema: {
        'slider': {
          'ui:widget': 'Slider'
        },
        'info': {

        }
      }
    }))
    // return new Promise((resolve) => {
    //   axios.get(`http://${origin}/api/anonymous/observation`, getHeaders())
    //       .then((response) => {
    //         dispatch(getObservationsAction(response.data))
    //         resolve()
    //       })
    //       .catch((error) => {
    //         errorHandle(error)
    //         resolve()
    //       })
    // })
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
      questionSchema: action.data
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
  questionSchema: {},
  observation: {}
}

export default function newobservationReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
