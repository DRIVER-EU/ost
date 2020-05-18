import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders } from '../../../store/addons'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}

export const GET_ALL_USERS_LIST = 'GET_ALL_USERS_LIST'
export const GET_ALL_USERS_LIST_FAIL = 'GET_ALL_USERS_LIST_FAIL'
export const GET_ALL_USERS_LIST_START = 'GET_ALL_USERS_LIST_START'
export const PUT_USER_START = 'PUT_USER_START'
export const PUT_USER_SUCCESS = 'PUT_USER_SUCCESS'
export const PUT_USER_FAIL = 'PUT_USER_FAIL'
export const GET_USER_SUCCESS = 'GET_USER_SUCCESS'
export const GET_USER_FAIL = 'GET_USER_FAIL'
export const GET_USER_START = 'GET_USER_START'
export const USER_PASSWORD_SUCCESS = 'USER_PASSWORD_SUCCESS'
export const USER_PASSWORD_FAIL = 'USER_PASSWORD_FAIL'

export const actions = {
  getAllUsersList,
  putUser,
  getSelectedUser,
  addUser,
  putUserPassword,
  passwordUpdateFailAction
}

export const allUsersListAction = (data) => {
  return {
    type: GET_ALL_USERS_LIST,
    data: data
  }
}

export const allUsersListStartAction = () => {
  return {
    type: GET_ALL_USERS_LIST_START,
    data: true
  }
}

export const allUsersListFailAction = () => {
  return {
    type: GET_ALL_USERS_LIST_FAIL,
    data: false
  }
}

export const updateUserStartAction = (data) => {
  return {
    type: PUT_USER_START
  }
}

export const updateUserSuccessAction = (data) => {
  return {
    type: PUT_USER_SUCCESS
  }
}

export const updateUserFailAction = (data) => {
  return {
    type: PUT_USER_FAIL
  }
}

export const getUserStartAction = (data) => {
  return {
    type: GET_USER_START,
    data: data
  }
}

export const getUserSuccessAction = (data) => {
  return {
    type: GET_USER_SUCCESS,
    data: data
  }
}

export const getUserFailAction = (data) => {
  return {
    type: GET_USER_FAIL
  }
}

export const passwordUpdateSuccessAction = () => {
  return {
    type: USER_PASSWORD_SUCCESS
  }
}

export const passwordUpdateFailAction = () => {
  return {
    type: USER_PASSWORD_FAIL
  }
}

export const getAllUsersList = () => {
  return dispatch => {
    dispatch(allUsersListStartAction())
    // axios.get(`${origin}/api/auth/users?page=0&size=100&sort=login,asc&sort=lastName,desc`, getHeaders())
    axios.get(`${origin}/api/user/all?page=0&size=100&sort=id`, getHeaders())
    .then(res => {
      dispatch(allUsersListAction(res.data))
    })
    .catch(err => {
      dispatch(allUsersListFailAction())
      toastr.error('Failed to get users list Check network and try again', toastrOptions)
    })
  }
}

export const putUser = (user, userId) => {
  return dispatch => {
    dispatch(updateUserStartAction())
    axios.put(`${origin}/api/auth/users/${userId}`, user, getHeaders())
    .then(res => {
      dispatch(updateUserSuccessAction())
      toastr.success('User data are updated', toastrOptions)
      dispatch(getAllUsersList())
    })
    .catch(err => {
      dispatch(updateUserFailAction())
      toastr.error('Failed to update the user', toastrOptions)
    })
  }
}

export const putUserPassword = (userId, data) => {
  return dispatch => {
    axios.put(`${origin}/api/auth/users/${userId}/change-password`, data, getHeaders())
    .then(res => {
      dispatch(passwordUpdateSuccessAction())
      toastr.success('User password is updated', toastrOptions)
    })
    .catch(err => {
      toastr.error('Failed to update user password', toastrOptions)
    })
  }
}

export const getSelectedUser = (userId) => {
  return dispatch => {
    dispatch(getUserStartAction())
    axios.get(`${origin}/api/auth/users/${userId}`, getHeaders())
    .then(res => {
      dispatch(getUserSuccessAction(res.data))
    })
    .catch(err => {
      dispatch(getUserFailAction())
      toastr.error('Failed to get selected user Try again', toastrOptions)
    })
  }
}

export const addUser = (user) => {
  return dispatch => {
    dispatch(updateUserStartAction())
    axios.post(`${origin}/api/auth/users/`, user, getHeaders())
    .then(res => {
      dispatch(updateUserSuccessAction())
      dispatch(getAllUsersList())
      toastr.success('User added', toastrOptions)
    })
    .catch(err => {
      dispatch(updateUserFailAction())
      toastr.error('Failed to add user Check data and try again', toastrOptions)
    })
  }
}

const ACTION_HANDLERS = {
  [GET_ALL_USERS_LIST]: (state, action) => {
    return {
      ...state,
      allUsersList: action.data,
      allUsersListLoading: false
    }
  },
  [GET_ALL_USERS_LIST_START]: (state, action) => {
    return {
      ...state,
      allUsersListLoading: true
    }
  },
  [GET_ALL_USERS_LIST_FAIL]: (state, action) => {
    return {
      ...state,
      allUsersListLoading: false
    }
  },
  [PUT_USER_START]: (state) => {
    return {
      ...state,
      allUsersListLoading: true
    }
  },
  [PUT_USER_SUCCESS]: (state) => {
    return {
      ...state,
      allUsersListLoading: false
    }
  },
  [PUT_USER_FAIL]: (state) => {
    return {
      ...state,
      allUsersListLoading: false
    }
  },
  [GET_USER_START]: (state) => {
    return {
      ...state,
      isUserLoading: true
    }
  },
  [GET_USER_SUCCESS]: (state, action) => {
    return {
      ...state,
      selectedUser: action.data,
      isUserLoading: false
    }
  },
  [GET_USER_FAIL]: (state) => {
    return {
      ...state,
      isUserLoading: false
    }
  },
  [USER_PASSWORD_SUCCESS]: (state) => {
    return {
      ...state,
      isPasswordUpdated: true
    }
  },
  [USER_PASSWORD_FAIL]: (state) => {
    return {
      ...state,
      isPasswordUpdated: false
    }
  }
}
  // ------------------------------------
  // Reducer
  // ------------------------------------
const initialState = {
  allUsersList: {},
  selectedUser: {},
  allUsersListLoading: false,
  isUserLoading: false,
  isPasswordUpdated: false
}

export default function usersManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}

