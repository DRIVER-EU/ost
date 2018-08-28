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
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const NEW_SESSION = 'NEW_SESSION'
// ------------------------------------
// Actions
// ------------------------------------

export const newSessionAction = (data = null) => {
  return {
    type: NEW_SESSION,
    data: data
  }
}

export const actions = {
  newSession
}

export const newSession = (data) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.put(`http://${origin}/api/trialsessions/createNewSession`, data, getHeaders())
          .then((response) => {
            dispatch(newSessionAction(response.data))
            toastr.success('New Session', 'New Session was created!', toastrOptions)
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            toastr.error('New Session', 'Error!', toastrOptions)
            resolve()
          })
    })
  }
}
// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [NEW_SESSION]: (state, action) => {
    return {
      ...state,
      session: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  session: {}
}

export default function newsessionReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
