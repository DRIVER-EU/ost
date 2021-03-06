// ------------------------------------
// Constants
// ------------------------------------
import { origin } from './../../../config/Api'

import axios from 'axios'
export const LOG_IN = 'LOG_IN'
export const LOG_OUT = 'LOG_OUT'
import { toastr } from 'react-redux-toastr'
import { browserHistory } from 'react-router'

const getHeaders = () => {
  let token = localStorage.getItem('drivertoken')
  let globalHeaders = { headers: { 'x-auth-token': token } }
  return globalHeaders
}

const toastrOptions = {
  timeOut: 3000
}

// ------------------------------------
// Actions
// ------------------------------------

export const logInAction = (user) => {
  return {
    type: LOG_IN,
    user: user
  }
}

export const logOutAction = () => {
  return {
    type: LOG_OUT
  }
}

export const actions = {
  logIn,
  logOut
}

export const logIn = (username, password) => {
  const hash = new Buffer(username + `:` + password).toString('base64')
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`${origin}/api/auth/login`, { headers: { 'Authorization': `Basic ` + hash } })
        .then((response) => {
          localStorage.setItem('drivertoken', response.headers['x-auth-token'])
          localStorage.setItem('driveruser', JSON.stringify(response.data))
          localStorage.setItem('driverrole', response.data.roles[0])
          localStorage.setItem('openTrial', false)
          localStorage.setItem('online', true)
          toastr.success('Login', 'Login correct!', toastrOptions)
          dispatch(logInAction(response.data))
          resolve()
        })
        .catch((error) => {
          if (error.message === 'Network Error') {
            toastr.error('Login', 'You cannot log in while being offline', toastrOptions)
          } else {
            toastr.error('Login', 'Wrong login or password. Try again', toastrOptions)
          }
          resolve()
        })
    })
  }
}

export const logOut = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`${origin}/api/auth/logout`, getHeaders())
        .then(() => {
          window.indexedDB.open('driver', 1).onsuccess = (event) => {
            for (let i = 0; i < event.target.result.objectStoreNames.length; i++) {
              event.target.result.transaction(event.target.result.objectStoreNames[i], 'readwrite')
                .objectStore(event.target.result.objectStoreNames[i]).clear()
            }
          }
          localStorage.clear()
          toastr.success('Logout', 'Logout correct!', toastrOptions)
          dispatch(logOutAction())
          resolve()
          browserHistory.push('/')
        })
        .catch((error) => {
          if (error.message === 'Network Error') {
            toastr.error('Login', 'You cannot log out while being offline', toastrOptions)
          } else {
            toastr.error('Logout', 'Error!', toastrOptions)
          }
          resolve()
        })
    })
  }
}

export const checkLogin = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`${origin}/api/trialsessions/active`, getHeaders())
        .then(() => {
          resolve()
        })
        .catch((error) => {
          if (error.message !== 'Network Error') {
            localStorage.clear()
            dispatch(logOutAction())
          }
          resolve()
        })
    })
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [LOG_IN]: (state, action) => {
    return {
      ...state,
      isLoggedIn: true,
      user: action.user
    }
  },
  [LOG_OUT]: (state, action) => { return { ...state, isLoggedIn: false, user: null } }
}

// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  isLoggedIn: !!localStorage.getItem('drivertoken'),
  user: JSON.parse(localStorage.getItem('driveruser'))
}

export default function loginReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]
  return handler ? handler(state, action) : state
}
