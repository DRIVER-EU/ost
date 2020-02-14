// ------------------------------------
// Constants
// ------------------------------------

import { origin } from '../../../config/Api'
import axios from 'axios'
import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_ROLE = 'GET_ROLE'
export const UPDATE_ROLE = 'UPDATE_ROLE'
export const REMOVE_ROLE = 'REMOVE_ROLE'
export const ASSIGN_QUESTION = 'ASSIGN_QUESTION'
export const UNASSIGN_QUESTION = 'UNASSIGN_QUESTION'
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
export const assignQuestionAction = (data = null) => {
  return {
    type: ASSIGN_QUESTION,
    data: data
  }
}
export const unassignQuestionAction = (data = null) => {
  return {
    type: UNASSIGN_QUESTION,
    data: data
  }
}

export const actions = {
  getRoleDetailAction,
  updateRoleAction,
  removeRoleAction,
  assignQuestionAction,
  unassignQuestionAction
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
export const assignQuestion = question => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .post(
          `${origin}/api/observationtypestrialrole/admin/addNewObservationTypeTrailRole`,
          question,
          getHeaders()
        )
        .then(response => {
          dispatch(assignQuestionAction(response.data))
          resolve()
        })
        .catch(error => {
          errorHandle(error)
          resolve()
        })
    )
  }
}
export const unassignQuestion = (roleId, questionId) => {
  return dispatch => {
    return new Promise(resolve =>
      axios
        .delete(
          // eslint-disable-next-line max-len
          `${origin}/api/observationtypestrialrole/admin/deleteObservationTypeTrailRole?trialRoleId=${roleId}&observationTypeId=${questionId}`,
          getHeaders()
        )
        .then(response => {
          dispatch(unassignQuestionAction(response.data))
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
      userRoles: action.data.userRoles,
      unassignedQuestions: action.data.unAssignedQuestions
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
  },
  [ASSIGN_QUESTION]: (state, action) => {
    return {
      ...state
    }
  },
  [UNASSIGN_QUESTION]: (state, action) => {
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
  userRoles: [],
  unassignedQuestions: []
}

export default function roleDetailReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
