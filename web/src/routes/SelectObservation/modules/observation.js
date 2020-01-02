// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle, freeQueue } from '../../../store/addons'

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
      axios.get(`${origin}/api/observationtypes?trialsession_id=${trialSessionId}`, getHeaders())
          .then((response) => {
            freeQueue()
            window.indexedDB.open('driver', 1).onsuccess = (event) => {
              let store = event.target.result.transaction(['observation_type'],
                'readwrite').objectStore('observation_type')
              // let del = store.clear()
              // del.onsuccess = (xevnt) => {
              //   store = event.target.result.transaction(['observation_type'],
              //   'readwrite').objectStore('observation_type')
              for (let i = 0; i < response.data.length; i++) {
                store.add(Object.assign(response.data[i],
                        { trialsession_id: trialSessionId }))
              }
             // }
            }
            dispatch(getObservationsAction(response.data))
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              window.indexedDB.open('driver', 1).onsuccess = (event) => {
                event.target.result.transaction(['observation_type'], 'readonly').objectStore('observation_type')
                .index('trialsession_id').getAll(trialSessionId).onsuccess = e1 => {
                  dispatch(getObservationsAction(e1.target.result))
                }
              }
            }
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
