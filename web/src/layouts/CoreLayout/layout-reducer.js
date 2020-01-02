import { GET_TRIAL_INFO, GET_BORDER_INFO } from './layout-action'

let defaultState = {
  trial: undefined,
  borderInfo: '---'
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
  } else {
    return {
      ...state
    }
  }
}

export default layoutReducer
