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
         let DBOpenRequest = window.indexedDB.open('driver', 1)
         DBOpenRequest.onsuccess = (event) => {
           let db = event.target.result
           let transaction = db.transaction(['trial_session'], 'readwrite')
           let store = transaction.objectStore('trial_session')
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
         let DBOpenRequest = window.indexedDB.open('driver', 1)
         DBOpenRequest.onsuccess = (event) => {
           let db = event.target.result
           let transaction = db.transaction(['trial_session'], 'readonly')
           let store = transaction.objectStore('trial_session').index('status')
           store.get('ACTIVE').onsuccess = (event) => {
             dispatch(getTrialsAction({ total: event.target.result.length, data: event.target.result }))
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
