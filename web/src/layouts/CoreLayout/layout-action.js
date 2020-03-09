import { origin } from '../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../store/addons'

export const GET_TRIAL_INFO = 'GET_TRIAL_INFO'
export const GET_BORDER_INFO = 'GET_BORDER_INFO'

export const getTrialInfo = (user) => {
  return {
    type: GET_TRIAL_INFO,
    trial: user
  }
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
