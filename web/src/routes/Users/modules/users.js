import { origin } from './../../../config/Api'
import axios from 'axios'
import { getHeaders } from '../../../store/addons'
// import { toastr } from 'react-redux-toastr'

// const toastrOptions = {
//   timeOut: 3000
// }

export const GET_ALL_USERS_LIST = 'GET_ALL_USERS_LIST'
export const GET_ALL_USERS_LIST_FAIL = 'GET_ALL_USERS_LIST_FAIL'
export const GET_ALL_USERS_LIST_START = 'GET_ALL_USERS_LIST_START'
export const PUT_USER_START = 'PUT_USER_START'
export const PUT_USER_SUCCESS = 'PUT_USER_SUCCESS'
export const PUT_USER_FAIL = 'PUT_USER_FAIL'

export const actions = {
  getAllUsersList
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

export const getAllUsersList = () => {
  return dispatch => {
    dispatch(allUsersListStartAction())
    axios.get(`${origin}/api/auth/users?page=0&sort=login,asc&sort=lastName,desc`, getHeaders())
    .then(res => {
      dispatch(allUsersListAction(res.data))
    })
    .catch(err => {
      dispatch(allUsersListFailAction())
      console.log('ERROR :: ', err)
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
  }
}
  // ------------------------------------
  // Reducer
  // ------------------------------------
const initialState = {
  allUsersList: {},
  allUsersListLoading: false
}

export default function usersManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}

