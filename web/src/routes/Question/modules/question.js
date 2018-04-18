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
    console.log(2)
    dispatch(getSchemaAction({
      'id': 1,
      'name': 'Situational awareness',
      'description': 'Good situational awareness is observed if location ' +
      'basedinformation is well handled and no mistakes are made between (pieces of) ' +
      'information received. Poor situationalawareness is observed when location based is missed, ' +
     ' mixed up, incomplete or incorretly handled.',
      'users': [
        {
          'id': 2,
          'firstName': 'Mayer',
          'lastName': 'Zandecki'
        },
        {
          'id': 3,
          'firstName': 'John',
          'lastName': 'Zandecki'
        },
        {
          'id': 4,
          'firstName': 'Doe',
          'lastName': 'Zandecki'
        }
      ],
      'jsonSchema': {
        'schema': {
          'type': 'object',
          'properties': {
            'question_8': {
              'title': 'Mark good or poor awareness',
              'description': 'Decide if there were any mistakes made',
              'type': 'string',
              'enum': [
                'good awareness',
                'poor awareness'
              ]
            },
            'question_10': {
              'title': 'Because I observed...',
              'description': 'Decide if there were any mistakes made',
              'type': 'string'
            },
            'question_12': {
              'title': 'Because I observed...',
              'description': 'Decide if there were any mistakes made',
              'type': 'boolean'
            }
          }
        },
        'uiSchema': {
          'question_8': {
            'ui:disabled': false,
            'ui:widget': 'radio'
          },
          'question_10': {
            'ui:disabled': false
          },
          'question_12': {
            'ui:disabled': false
          }
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
