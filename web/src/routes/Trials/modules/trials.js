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
         DBOpenRequest.onsuccess = () => {
           let idb = DBOpenRequest.result
           console.log('sowa 2: ', response.data.data, idb)
           let trialsessionsActive = idb.transaction('trialsessionsActive', 'readwrite')
           let objectStore = trialsessionsActive.objectStore('trialsessionsActive')
           for (let i = 0; i < response.data.data.length; i++) {
             let request = objectStore.add(response.data.data[i])
             request.onsuccess = () => { console.log(':)') }
             request.onerror = () => { console.error('Add error: ', request.error) }
           }

           trialsessionsActive.oncomplete = () => {
             console.log('trialsessionsActive complete :D')
           }

           trialsessionsActive.onerror = () => {
             console.error('trialsessionsActive error: ', trialsessionsActive)
           }
         }
         dispatch(getTrialsAction(response.data))
         resolve()
       })
       .catch((error) => {
         errorHandle(error)
         dispatch(getTrialsAction(JSON.parse(localStorage.getItem('listOfTrials') || '{ total: 0, data: [] }')))
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
