import { GET_TRIAL_INFO, GET_BORDER_INFO, CHECK_CONNECTION } from './layout-action'

let defaultState = {
  trial: undefined,
  borderInfo: '---',
  isConnected: false
}

const layoutReducer = (state = defaultState, action) => {
  if (action.type === GET_TRIAL_INFO) {
    return {
      ...state,
      trial: action.trial
    }
  } if (action.type === GET_BORDER_INFO) {
    return {
      ...state,
      borderInfo: action.borderInfo
    }
  } if (action.type === CHECK_CONNECTION) {
    return {
      ...state,
      isConnected: action.isConnected
    }
  } else {
    return {
      ...state
    }
  }
}

export default layoutReducer
