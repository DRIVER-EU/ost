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

export const GET_VIEW_TRIALS = 'GET_VIEW_TRIALS'
// ------------------------------------
// Actions
// ------------------------------------

export const getViewTrialsAction = (data = null) => {
  return {
    type: GET_VIEW_TRIALS,
    data: data
  }
}

export const actions = {
  getViewTrials
}

export const getViewTrials = (trialsessionId) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/answers-events?trialsession_id=${trialsessionId}`, getHeaders())
       .then((response) => {
         dispatch(getViewTrialsAction(response.data))
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
  [GET_VIEW_TRIALS]: (state, action) => {
    return {
      ...state,
      viewTrials: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  viewTrials: []
}

export default function viewTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
