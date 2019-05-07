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
import { getHeaders, errorHandle } from '../../../store/addons'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const GET_VIEW_TRIALS = 'GET_VIEW_TRIALS'
export const GET_TRIAL_SESSION = 'GET_TRIAL_SESSION'
export const GET_TRIALS = 'GET_TRIALS'
export const CLEAR_TRIALS = 'CLEAR_TRIALS'
export const REMOVE_ANSWER = 'REMOVE_ANSWER'
export const EDIT_COMMENT = 'EDIT_COMMENT'
// ------------------------------------
// Actions
// ------------------------------------

export const getViewTrialsAction = (data = null) => {
  return {
    type: GET_VIEW_TRIALS,
    data: data
  }
}

export const getTrialSessionAction = (data = null) => {
  return {
    type: GET_TRIAL_SESSION,
    data: data
  }
}

export const getTrialsAction = (data = null) => {
  return {
    type: GET_TRIALS,
    data: data
  }
}

export const clearTrialsAction = (data = null) => {
  return {
    type: CLEAR_TRIALS,
    data: []
  }
}

export const removeAnswerAction = (data = null) => {
  return {
    type: REMOVE_ANSWER,
    data: data
  }
}

export const editCommentAction = (data = null) => {
  return {
    type: EDIT_COMMENT,
    data: data
  }
}

export const actions = {
  getViewTrials,
  getTrials,
  removeAnswer,
  editComment
}

export const getViewTrials = (trialsessionId) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/answers-events?trialsession_id=${trialsessionId}`, getHeaders())
       .then((response) => {
         dispatch(getViewTrialsAction(response.data))
         resolve()
       })
       .catch((error) => {
         errorHandle(error)
         resolve()
       })
    })
  }
}

export const getTrialSession = (trialsessionId) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions/${trialsessionId}`, getHeaders())
       .then((response) => {
         dispatch(getTrialSessionAction(response.data))
         resolve()
       })
       .catch((error) => {
         errorHandle(error)
         resolve()
       })
    })
  }
}

export const getTrials = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.get(`http://${origin}/api/trialsessions/active`, getHeaders())
       .then((response) => {
         dispatch(getTrialsAction(response.data))
         resolve()
       })
       .catch((error) => {
         errorHandle(error)
         resolve()
       })
    })
  }
}

export const clearTrialList = () => {
  return (dispatch) => {
    return new Promise((resolve) => {
      dispatch(clearTrialsAction([]))
      resolve()
    })
  }
}

export const removeAnswer = (id, comment) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      axios.delete(`http://${origin}/api/answers/${id}/remove?comment=${comment}`, getHeaders())
        .then(() => {
          toastr.success('Remove answer', 'Action removing answer or event has been successful!', toastrOptions)
          dispatch(removeAnswerAction())
          resolve()
        })
        .catch((error) => {
          errorHandle(error)
          resolve(error)
        })
    })
  }
}

export const editComment = (id, comment) => {
  return (dispatch) => {
    return new Promise((resolve) => {
      const data = new FormData()
      data.append('comment', comment)
      axios.post(`http://${origin}/api/questions-answers/${id}/comment`, data, getHeaders())
          .then((response) => {
            dispatch(editCommentAction(response.data))
            toastr.success('Comment', 'Changed was save!', toastrOptions)
            resolve()
          })
          .catch((error) => {
            errorHandle(error)
            toastr.error('Comment', 'Error!', toastrOptions)
            resolve()
          })
    })
  }
}
// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_VIEW_TRIALS]: (state, action) => {
    return {
      ...state,
      viewTrials: action.data
    }
  },
  [GET_TRIAL_SESSION]: (state, action) => {
    return {
      ...state,
      trialSession: action.data
    }
  },
  [GET_TRIALS]: (state, action) => {
    return {
      ...state,
      listOfTrials: action.data
    }
  },
  [CLEAR_TRIALS]: (state, action) => {
    return {
      ...state,
      listOfTrials: action.data
    }
  },
  [EDIT_COMMENT]: (state, action) => {
    return {
      ...state,
      eidtedComment: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  viewTrials: [],
  trialSession: {},
  listOfTrials: {},
  eidtedComment: {}
}

export default function viewTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
