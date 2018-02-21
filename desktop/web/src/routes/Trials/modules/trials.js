// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = '192.168.1.15:8080'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_OBSERVATION = 'GET_OBSERVATION'
// ------------------------------------
// Actions
// ------------------------------------

export const getObservationAction = (data = null) => {
  return {
    type: GET_OBSERVATION,
    data: data
  }
}

export const actions = {
  getObservation
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
  observation: []
}

export default function trialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
