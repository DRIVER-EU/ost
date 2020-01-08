// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import {
  getHeaders,
  errorHandle,
  freeQueue,
  getHeadersReferences
} from '../../../store/addons'

export const GET_TRIALMANAGER = 'GET_TRIALMANAGER'
export const GET_LIST_OF_TRIALS = 'GET_LIST_OF_TRIALS'
export const ADD_TRIAL = 'ADD_TRIAL'
// ------------------------------------
// Actions
// ------------------------------------
export const addTrial = (data = null) => {
  return {
    type:ADD_TRIAL,
    data:data
  }
}

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
  getListOfTrials,
  importFile
}

export const getTrialManager = () => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .get(`${origin}/api/admin/ostTrialsAll`, getHeaders())
        .then(response => {
          freeQueue()
          window.indexedDB.open('driver', 1).onsuccess = event => {
            let store = event.target.result
              .transaction(['trial_session'], 'readwrite')
              .objectStore('trial_session')
            for (let i = 0; i < response.data.trialsSet.length; i++) {
              store.get(response.data.trialsSet[i].id).onsuccess = x => {
                if (!x.target.result) {
                  store.add(response.data.trialsSet[i])
                }
              }
            }
          }
          dispatch(getTrialManagerAction(response.data))
          resolve()
        })
        .catch(error => {
          if (error.message === 'Network Error') {
            window.indexedDB.open('driver', 1).onsuccess = event => {
              event.target.result
                .transaction(['trial_session'], 'readonly')
                .objectStore('trial_session')
                .getAll().onsuccess = e => {
                  dispatch(
                  getTrialManagerAction(
                    e.target.result && e.target.result.length
                      ? {
                        total: e.target.result.length,
                        data: e.target.result
                      }
                      : {
                        total: 1,
                        data: [e.target.result]
                      }
                  )
                )
                }
            }
          }
          errorHandle(error)
          resolve()
        })
    })
  }
}

export const getListOfTrials = () => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .get(`${origin}/api/trialsessions/trials`, getHeaders())
        .then(response => {
          freeQueue()
          localStorage.setItem('listOfTrials', JSON.stringify(response.data))
          dispatch(getListOfTrialsAction(response.data))
          resolve()
        })
        .catch(error => {
          if (error.message === 'Network Error') {
            let list = localStorage.getItem('listOfTrials')
            if (list) {
              dispatch(getListOfTrialsAction(JSON.parse(list)))
            }
          }
          errorHandle(error)
          resolve()
        })
    })
  }
}

// Once backend is fixed
const realPath = '/import'
export const importFile = formData => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .put(`${origin}${realPath}`, formData, getHeadersReferences())
        .then(response => {
          window.indexedDB.open('driver', 1).onsuccess = event => {
            var { data } = response
            var newTrial = { id:data.id, name:data.name }

            event.target.result
              .transaction(['trial_session'], 'readwrite')
              .objectStore('trial_session')
              .put(newTrial).onsuccess = e => {
                dispatch(addTrial(newTrial))
              }
          }
          resolve()
        })
        .catch(error => {
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
      listOfTrials2: action.data
    }
  },
  [ADD_TRIAL]:(state, action) => {
    return {
      ...state,
      listOfTrialsManager: {
        ...state.listOfTrialsManager,
        trialsSet: [...state.listOfTrialsManager.trialsSet, action.data]
      }
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  listOfTrialsManager: {},
  listOfTrials2: {}
}

export default function trialManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
