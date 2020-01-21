// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import { toastr } from 'react-redux-toastr'
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
    type: ADD_TRIAL,
    data: data
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
            var mappedResponse = response.data.map(x => ({
              id: x.trialId,
              name: x.trialName,
              description: x.trialDescription,
              archived: x.archived
            }))
            for (let i = 0; i < mappedResponse.length; i++) {
              store.get(response.data[i].trialId).onsuccess = x => {
                if (!x.target.result) {
                  store.add(mappedResponse[i])
                }
              }
            }
            dispatch(getTrialManagerAction(mappedResponse))
            resolve()
          }
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

const realPath = 'import'
export const importFile = formData => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .put(`${origin}/api/${realPath}`, formData, getHeadersReferences())
        .then(response => {
          window.indexedDB.open('driver', 1).onsuccess = event => {
            var { data } = response
            var newTrial = { id: data.id, name: data.name }
            if (data.status !== 'BAD_REQUEST') {
              event.target.result
                .transaction(['trial_session'], 'readwrite')
                .objectStore('trial_session')
                .put(newTrial).onsuccess = e => {
                  toastr.success('Import', 'Import sucessful!')
                  dispatch(addTrial(newTrial))
                }
            }
            resolve({
              errors: data.errors,
              warnings: data.warnings
            })
          }
        })
        .catch(error => {
          toastr.error('Import', 'Import failed!')
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
      listOfTrialsManager: {
        ...state.listOfTrialsManager,
        trialsSet: action.data
      }
    }
  },
  [GET_LIST_OF_TRIALS]: (state, action) => {
    return {
      ...state,
      listOfTrials2: action.data
    }
  },
  [ADD_TRIAL]: (state, action) => {
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
