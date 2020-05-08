import { origin } from '../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../store/addons'
import { logInAction, logOutAction } from '../../routes/Login/modules/login'

export const GET_TRIAL_INFO = 'GET_TRIAL_INFO'
export const GET_BORDER_INFO = 'GET_BORDER_INFO'

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

export const getBorderInfoAction = (data) => {
  return {
    type: GET_BORDER_INFO,
    borderInfo: data.Border
  }
}
