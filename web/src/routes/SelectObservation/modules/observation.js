// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
  origin = '193.142.112.117:83' // 'dev.itti.com.pl:8009'
} else {
  origin = window.location.host
}
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_OBSERVATIONS = 'GET_OBSERVATIONS'
// ------------------------------------
// Actions
// ------------------------------------

export const getObservationsAction = (data = null) => {
  return {
    type: GET_OBSERVATIONS,
    data: data
  }
}

export const actions = {
  getObservations
}

export const getObservations = (trialSessionId) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/observationtypes?trialsession_id=${trialSessionId}`, getHeaders())
          .then((response) => {
            dispatch(getObservationsAction(response.data))
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            let offline = localStorage.getItem('observationList')
            if (offline) {
              console.log('sowa: ', offline)
              getObservationsAction(offline)
            }
            resolve()
          })
    })
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_OBSERVATIONS]: (state, action) => {
    return {
      ...state,
      listOfObservations: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  listOfObservations: []
}

export default function selectReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
