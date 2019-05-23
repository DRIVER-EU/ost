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
import { getHeaders, errorHandle, freeQueue } from '../../../store/addons'

export const GET_TRIALS = 'GET_TRIALS'
// ------------------------------------
// Actions
// ------------------------------------

export const getTrialsAction = (data = null) => {
  return {
    type: GET_TRIALS,
    data: data
  }
}

export const actions = {
  getTrials
}

export const getTrials = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions/active`, getHeaders())
       .then((response) => {
         freeQueue()
         window.indexedDB.open('driver', 1).onsuccess = (event) => {
           let store = event.target.result.transaction(['trial_session'], 'readwrite').objectStore('trial_session')
           for (let i = 0; i < response.data.data.length; i++) {
             let item = store.get(response.data.data[i].id)
             item.onsuccess = (x) => {
               if (!x.target.result) { store.add(response.data.data[i]) }
             }
           }
         }
         dispatch(getTrialsAction(response.data))
         resolve()
       })
       .catch((error) => {
         if (error.message === 'Network Error') {
           window.indexedDB.open('driver', 1).onsuccess = (event) => {
             event.target.result.transaction(['trial_session'],
             'readonly').objectStore('trial_session').index('status').getAll('ACTIVE').onsuccess = (e) => {
               dispatch(getTrialsAction(e.target.result && e.target.result.length
                 ? { total: e.target.result.length, data: e.target.result }
                 : { total: 1, data: [e.target.result] }))
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
  [GET_TRIALS]: (state, action) => {
    return {
      ...state,
      listOfTrials: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  listOfTrials: {}
}

export default function trialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
