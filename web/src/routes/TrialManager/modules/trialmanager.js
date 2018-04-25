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

export const GET_TRIALMANAGER = 'GET_TRIALMANAGER'
// ------------------------------------
// Actions
// ------------------------------------

export const getTrialManagerAction = (data = null) => {
  return {
    type: GET_TRIALMANAGER,
    data: data
  }
}

export const actions = {
  getTrialManager
}

export const getTrialManager = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions`, getHeaders())
       .then((response) => {
         dispatch(getTrialManagerAction(response.data))
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
  [GET_TRIALMANAGER]: (state, action) => {
    return {
      ...state,
      listOfTrialsManager: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  listOfTrialsManager: {}
}

export default function trialManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
