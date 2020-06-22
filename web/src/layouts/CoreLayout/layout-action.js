import { origin } from '../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../store/addons'
import { logInAction, logOutAction } from '../../routes/Login/modules/login'
// import { browserHistory } from 'react-router'

export const GET_TRIAL_INFO = 'GET_TRIAL_INFO'
export const GET_BORDER_INFO = 'GET_BORDER_INFO'
export const CHECK_CONNECTION = 'CHECK_CONNECTION'

export const getTrialInfo = (user) => {
  return {
    type: GET_TRIAL_INFO,
    trial: user
  }
}

export const logIn = user => {
  return (dispatch) => dispatch(logInAction(user))
}

export const logOut = () => {
  return (dispatch) => dispatch(logOutAction())
}

export const getBorderInfo = (message) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`${origin}/api/admin/border/lower`, getHeaders())
        .then((response) => {
          dispatch(getBorderInfoAction(response.data))
          resolve()
        })
        .catch((error) => {
          errorHandle(error)
          resolve()
        })
    })
  }
}

export const checkConnection = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`${origin}/api/trialsessions/active`, getHeaders())
        .then(() => {
          dispatch(checkConnectionAction(true))
          resolve()
        })
        .catch((error) => {
          if (error.message === 'Network Error') {
            dispatch(checkConnectionAction(false))
            // localStorage.removeItem('driveruser')
            // localStorage.removeItem('drivertoken')
            // localStorage.removeItem('driverrole')
            // dispatch(logOutAction())
            // browserHistory.push('/')
          } else {
            dispatch(checkConnectionAction(true))
          }
          resolve()
        })
    })
  }
}

export const checkConnectionAction = (isConnected) => {
  return {
    type: CHECK_CONNECTION,
    isConnected
  }
}

export const getBorderInfoAction = (data) => {
  return {
    type: GET_BORDER_INFO,
    borderInfo: data.Border
  }
}
