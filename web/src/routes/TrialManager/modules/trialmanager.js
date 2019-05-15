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
export const GET_LIST_OF_TRIALS = 'GET_LIST_OF_TRIALS'
// ------------------------------------
// Actions
// ------------------------------------
export const getTrialManagerAction = (data = null) => {
  return {
    type: GET_TRIALMANAGER,
    data: data
  }
}

export const getListOfTrialsAction = (data = null) => {
  return {
    type: GET_LIST_OF_TRIALS,
    data: data
  }
}

export const actions = {
  getTrialManager,
  getListOfTrials
}

export const getTrialManager = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions?size=1000`, getHeaders())
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
         console.log('sowa 7: ', response.data)
         dispatch(getTrialManagerAction(response.data))
         resolve()
       })
       .catch((error) => {
         let DBOpenRequest = window.indexedDB.open('driver', 1)
         DBOpenRequest.onsuccess = (event) => {
           let db = event.target.result
           let transaction = db.transaction(['trial_session'], 'readonly')
           let store = transaction.objectStore('trial_session')
           store.getAll().onsuccess = (e) => {
             console.log('sowa 8: ', e)
             let result = e.target.result.length ? { total: e.target.result.length, data: e.target.result }
              : { total: 1, data: [e.target.result] }
             dispatch(getTrialManagerAction(result))
           }
         }
         errorHandle(error)
         resolve()
       })
    })
  }
}

export const getListOfTrials = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions/trials`, getHeaders())
       .then((response) => {
         console.log('sowa 9: ', response.data)
         dispatch(getListOfTrialsAction(response.data))
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
  },
  [GET_LIST_OF_TRIALS]: (state, action) => {
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
  listOfTrialsManager: {},
  listOfTrials: {}
}

export default function trialManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
