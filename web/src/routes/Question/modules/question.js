// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl:8009') {
  origin = 'dev.itti.com.pl:8009'
} else {
  origin = 'dev.itti.com.pl:8009'
}
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

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

export const getViewSchema = (idObs, idSession) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      /* eslint-disable */
      axios.get(`http://${origin}/api/observationtypes/form?observationtype_id=${idObs}&trialsession_id=${idSession}`, getHeaders())
      /* eslint-enable */
          .then((response) => {
            dispatch(getViewSchemaAction(response.data))
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
