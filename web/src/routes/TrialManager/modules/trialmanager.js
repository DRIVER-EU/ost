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
         window.indexedDB.open('driver', 1).onsuccess = (event) => {
           let store = event.target.result.transaction(['trial_session'], 'readwrite').objectStore('trial_session')
           for (let i = 0; i < response.data.data.length; i++) {
             store.get(response.data.data[i].id).onsuccess = (x) => {
               if (!x.target.result) { store.add(response.data.data[i]) }
             }
           }
         }
         dispatch(getTrialManagerAction(response.data))
         resolve()
       })
       .catch((error) => {
         window.indexedDB.open('driver', 1).onsuccess = (event) => {
           event.target.result.transaction(['trial_session'],
           'readonly').objectStore('trial_session').getAll().onsuccess = (e) => {
             dispatch(getTrialManagerAction(e.target.result && e.target.result.length
              ? { total: e.target.result.length, data: e.target.result }
              : { total: 1, data: [e.target.result] }))
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
         window.indexedDB.open('driver', 1).onsuccess = (event) => {
           let store = event.target.result.transaction(['trial'], 'readwrite').objectStore('trial')
           for (let i = 0; i < response.data.data.length; i++) {
             store.get(response.data.data[i].id).onsuccess = (x) => {
               if (!x.target.result) { store.add(response.data.data[i]) }
             }
           }
         }
         dispatch(getListOfTrialsAction(response.data))
         resolve()
       })
       .catch((error) => {
         window.indexedDB.open('driver', 1).onsuccess = (event) => {
           event.target.result.transaction(['trial'],
          'readonly').objectStore('trial').getAll().onsuccess = (e) => {
            dispatch(getTrialManagerAction(e.target.result && e.target.result.length
             ? { total: e.target.result.length, data: e.target.result }
             : { total: 1, data: [e.target.result] }))
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
