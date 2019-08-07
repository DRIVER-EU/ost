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
import { getHeaders, getHeadersASCI, getHeadersReferences, errorHandle, freeQueue } from '../../../store/addons'
import fileDownload from 'react-file-download'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const GET_SCHEMA = 'GET_SCHEMA'
export const SEND_OBSERVATION = 'SEND_OBSERVATION'
export const DOWNLOAD_FILE = 'DOWNLOAD_FILE'
export const GET_TRIAL_TIME = 'GET_TRIAL_TIME'

// ------------------------------------
// Actions
// ------------------------------------

export const getSchemaAction = (data = null) => {
  return {
    type: GET_SCHEMA,
    data: data
  }
}

export const sendObservationAction = (data = null) => {
  return {
    type: SEND_OBSERVATION,
    data: data
  }
}

export const downloadFileAction = (data = null) => {
  return {
    type: DOWNLOAD_FILE,
    data: data
  }
}

export const getTrialTimeAction = (data = null) => {
  return {
    type: GET_TRIAL_TIME,
    data: data
  }
}

export const actions = {
  getSchema,
  sendObservation,
  downloadFile,
  getTrialTime
}

export const getSchema = (idObs, idSession) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(
        `https://${origin}/api/observationtypes/form?observationtype_id=${idObs}&trialsession_id=${idSession}`,
        getHeaders())
          .then((response) => {
            freeQueue()
            window.indexedDB.open('driver', 1).onsuccess = (event) => {
              let store = event.target.result.transaction(['observation_type'],
                'readwrite').objectStore('observation_type')
              store.get(response.data.id).onsuccess = (x) => {
                if (!x.target.result) {
                  store.add(Object.assign(response.data,
                    { trialsession_id: idSession, observationtype_id: idObs }))
                } else {
                  store.delete(response.data.id).onsuccess = () => {
                    store.add(Object.assign(response.data,
                      { trialsession_id: idSession, observationtype_id: idObs }))
                  }
                }
              }
            }
            dispatch(getSchemaAction(response.data))
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              window.indexedDB.open('driver', 1).onsuccess = (event) => {
                event.target.result.transaction(['observation_type'],
                'readonly').objectStore('observation_type').index('trialsession_id, observationtype_id')
                .get([idSession, idObs]).onsuccess = (e) => { dispatch(getSchemaAction(e.target.result)) }
              }
            }
            errorHandle(error)
            resolve()
          })
    })
  }
}

export const sendObservation = (formData) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      const data = new FormData()
      let tempData = {}
      for (let key in formData) {
        if (key !== 'attachments') {
          tempData[key] = formData[key]
        }
      }
      let json = JSON.stringify(tempData)
      let blob = new Blob([json], { type: 'application/json' })
      for (var i = 0; i < formData.attachments.length; i++) {
        // add each file to the form data and iteratively name them
        data.append('attachments', formData.attachments[i])
      }

     // data.append('attachments', formData.attachments)
      data.append('data', blob)
      axios.post(`https://${origin}/api/answers`, data, getHeadersReferences())
          .then((response) => {
            dispatch(sendObservationAction(response.data))
            toastr.success('Observation form', 'Observation was send!', toastrOptions)
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              toastr.warning('Offline mode', 'Message will be send later', toastrOptions)
              if (localStorage.getItem('online')) { localStorage.removeItem('online') }
              window.indexedDB.open('driver', 1).onsuccess = event => {
                let store = event.target.result.transaction(['sendQueue'], 'readwrite').objectStore('sendQueue')
                store.add({
                  type: 'post',
                  address: `https://${origin}/api/answers`,
                  data: formData,
                  headerType: 'refference'
                })
              }
            } else {
              toastr.error('Observation form', 'Error! Please, check all fields in form.', toastrOptions)
            }
            errorHandle(error)
            resolve()
          })
    })
  }
}

// backend errors
// leaving it as buggy as it is
export const downloadFile = (id, name) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`https://${origin}/api/attachments/${id}`, getHeadersASCI())
     .then((response) => {
       fileDownload(response.data, name)
       resolve()
     })
     .catch((error) => {
       errorHandle(error)
       resolve()
     })
    })
  }
}

export const getTrialTime = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`https://${origin}/api/trial-time`, getHeaders())
      .then((response) => {
        freeQueue()
        localStorage.setItem('trial-time', response.data)
        dispatch(getTrialTimeAction(response.data))
        resolve()
      })
      .catch((error) => {
        if (error.message === 'Network Error') {
          let res = localStorage.getItem('trial-time')
          if (res) { dispatch(getTrialTimeAction(res)) }
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
  [GET_SCHEMA]: (state, action) => {
    return {
      ...state,
      observationForm: action.data
    }
  },
  [SEND_OBSERVATION]: (state, action) => {
    return {
      ...state,
      observation: action.data
    }
  },
  [GET_TRIAL_TIME]: (state, action) => {
    return {
      ...state,
      trialTime: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  observationForm: {},
  observation: {},
  trialTime: null
}

export default function newobservationReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
