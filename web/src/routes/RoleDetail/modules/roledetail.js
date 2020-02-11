// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_ROLE = 'GET_ROLE'
export const UPDATE_ROLE = 'UPDATE_ROLE'
export const REMOVE_ROLE = 'REMOVE_ROLE'
// ------------------------------------
// Actions
// ------------------------------------
export const getRoleDetailAction = (data = null) => {
  return {
    type: GET_ROLE,
    data: data
  }
}

export const updateRoleAction = (data = null) => {
  return {
    type: UPDATE_ROLE,
    data: data
  }
}

export const removeRoleAction = (data = null) => {
  return {
    type: REMOVE_ROLE,
    data: data
  }
}

export const actions = {
  getRoleDetailAction,
  updateRoleAction,
  removeRoleAction
}
export const updateRole = role => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .put(`${origin}/api/role/admin/updateTrialRole`, role, getHeaders())
        .then(response => {
          dispatch(updateRoleAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const removeRole = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          `${origin}/api/role/admin/deleteTrialRole?id=${id}`,
          getHeaders()
        )
        .then(response => {
          dispatch(removeRoleAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

export const getRoleById = id => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .get(`${origin}/api/role/admin/getFullTrialRole?id=${id}`, getHeaders())
        .then(response => {
          dispatch(getRoleDetailAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_ROLE]: (state, action) => {
    return {
      ...state,
      id: action.data.id,
      roleName: action.data.name,
      roleType: action.data.roleType,
      trialId: action.data.trialId,
      questions: action.data.questions,
      userRoles: action.data.userRoles
    }
  },
  [UPDATE_ROLE]: (state, action) => {
    return {
      ...state,
      roleName: action.data.name,
      id: action.data.id,
      roleType: action.data.roleType,
      questions: action.data.questions,
      trialId: action.data.trialId
    }
  },
  [REMOVE_ROLE]: (state, action) => {
    return {
      ...state
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
  trialId: 0,
  questions: [],
  userRoles: []
}

export default function roleDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
