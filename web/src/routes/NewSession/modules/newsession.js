// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'
import fileDownload from 'react-file-download'
import { toastr } from 'react-redux-toastr'
import { browserHistory } from 'react-router'

const toastrOptions = {
  timeOut: 3000
}

export const NEW_SESSION = 'NEW_SESSION'
// ------------------------------------
// Actions
// ------------------------------------

export const newSessionAction = (data = null) => {
  return {
    type: NEW_SESSION,
    data: data
  }
}

export const actions = {
  newSession
}

export const newSession = (data, type) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      let url = ''
      if (type === 'email') {
        url = 'createNewSessionEmail'
      } else {
        url = 'createNewSessionFile'
      }
      axios.post(`${origin}/api/trialsessions/${url}`, data, getHeaders())
          .then((response) => {
            if (type === 'email') {
              dispatch(newSessionAction(response.data))
            } else {
              fileDownload(response.data, 'listOfUsers.txt')
              browserHistory.push('/trial-manager')
            }
            toastr.success('New Session', 'New Session was created!', toastrOptions)
            resolve()
          })
          .catch((error) => {
            if (error.message === 'Network Error') {
              toastr.warning('Offline mode', 'Message will be send later', toastrOptions)
              if (localStorage.getItem('online')) { localStorage.removeItem('online') }
              window.indexedDB.open('driver', 1).onsuccess = event => {
                event.target.result.transaction(['sendQueue'], 'readwrite').objectStore('sendQueue').add({
                  type: 'post',
                  address: `${origin}/api/trialsessions/${url}`,
                  data: data
                })
              }
            } else {
              browserHistory.push('/trial-manager')
              toastr.error('New Session', 'Error!', toastrOptions)
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
  [NEW_SESSION]: (state, action) => {
    return {
      ...state,
      session: new Date()
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  session: null
}

export default function newsessionReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
