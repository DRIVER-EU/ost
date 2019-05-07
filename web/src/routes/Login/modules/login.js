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
      axios.get(`http://${origin}/api/auth/login`, { headers: { 'Authorization': `Basic ` + hash } })
        .then((response) => {
          localStorage.setItem('drivertoken', response.headers['x-auth-token'])
          localStorage.setItem('driveruser', JSON.stringify(response.data))
          localStorage.setItem('driverrole', response.data.roles[0])
          localStorage.setItem('openTrial', false)
          toastr.success('Login', 'Login correct!', toastrOptions)
          dispatch(logInAction(response.data))
          resolve()
        })
        .catch((error) => {
          toastr.error('Login', 'Wrong login or password. Try again', toastrOptions)
          resolve()
        })
    })
  }
}

export const logOut = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/auth/logout`, getHeaders())
        .then((response) => {
          localStorage.removeItem('drivertoken')
          localStorage.removeItem('driveruser')
          localStorage.removeItem('driverrole')
          localStorage.removeItem('openTrial')
          toastr.success('Logout', 'Logout correct!', toastrOptions)
          dispatch(logOutAction())
          resolve()
          browserHistory.push('/')
        })
        .catch((error) => {
          toastr.error('Logout', 'Error!', toastrOptions)
          resolve()
        })
    })
  }
}

export const checkLogin = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions/active`, getHeaders())
        .then((response) => {
          resolve()
        })
        .catch((error) => {
          // localStorage.removeItem('drivertoken')
          // localStorage.removeItem('driveruser')
          // localStorage.removeItem('driverrole')
          // dispatch(logOutAction())
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
