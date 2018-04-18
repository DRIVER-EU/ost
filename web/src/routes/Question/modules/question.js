// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
// if (origin === 'localhost' || origin === '192.168.1.15:8080') {
//   origin = '192.168.1.15:8080'
// } else {
origin = '192.168.1.15:8080'
// }
// import axios from 'axios'
// import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_VIEWSCHEMA = 'GET_VIEWSCHEMA'
// ------------------------------------
// Actions
// ------------------------------------

export const getViewSchemaAction = (data = null) => {
  return {
    type: GET_VIEWSCHEMA,
    data: data
  }
}

export const actions = {
  getViewSchema
}

export const getViewSchema = () => {
  return (dispatch) => {
    dispatch(getViewSchemaAction({
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

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_VIEWSCHEMA]: (state, action) => {
    return {
      ...state,
      viewSchema: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  viewSchema: {}
}

export default function questionReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
