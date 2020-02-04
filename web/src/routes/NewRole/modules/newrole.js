// ------------------------------------
// Constants
// ------------------------------------

import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const ADD_NEW_ROLE = 'ADD_NEW_ROLE'
// ------------------------------------
// Actions
// ------------------------------------

export const addNewRoleAction = (data = null) => {
  return {
    type: ADD_NEW_ROLE,
    data: data
  }
}

export const addNewRole = (role) => {
  return dispatch => {
    return new Promise(resolve => {
      axios
        .post(`${origin}/api/role/admin/addNewTrialRole`, role, getHeaders())
        .then(response => {
          dispatch(addNewRoleAction(response.data))
          resolve()
        })
        .catch(error => {
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
  [ADD_NEW_ROLE]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      roleName: action.data.name,
      roleType: action.data.roleType,
      trialId: action.data.trialId
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  id: 0,
  roleName: '',
  roleType: '',
  trialId: 0
}

export default function newRoleDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
