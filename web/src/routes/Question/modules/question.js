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
            'max': 10,
            'value': 2,
            'step': 1
          }
        }
      },
      uiSchema: {
        'slider': {
          'ui:widget': 'Slider',
          'ui:disabled': true
        },
        'info': {
          'ui:disabled': true
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
