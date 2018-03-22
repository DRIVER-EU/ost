// ------------------------------------
// Constants
// ------------------------------------
export let origin = window.location.hostname
// if (origin === 'localhost' || origin === '192.168.1.15:8080') {
//   origin = '192.168.1.15:8080'
// } else {
origin = '192.168.1.15:8080'
// }
// import axios from 'axios'
// import { getHeaders, errorHandle } from '../../../store/addons'

export const GET_OBSERVATIONS = 'GET_OBSERVATIONS'
// ------------------------------------
// Actions
// ------------------------------------

export const getObservationsAction = (data = null) => {
  return {
    type: GET_OBSERVATIONS,
    data: data
  }
}

export const actions = {
  getObservations
}

export const getObservations = () => {
  return (dispatch) => {
    dispatch(getObservationsAction({
      total: 10,
      data: [
        { id: 1, title: 'Profile photo', desc: 'Change your Google+ profile photo' },
        { id: 2, title: 'Show your status', desc: 'Your status is visible to everyone you use with' },
        { id: 3, title: 'Profile porno', desc: 'Change your Boobs+ profile porno' }
      ]
    }))
    // return new Promise((resolve) => {
    //   axios.get(`http://${origin}/api/anonymous/observation`, getHeaders())
    //       .then((response) => {
    //         dispatch(getObservationsAction(response.data))
    //         resolve()
    //       })
    //       .catch((error) => {
    //         errorHandle(error)
    //         resolve()
    //       })
    // })
  }
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
  [GET_OBSERVATIONS]: (state, action) => {
    return {
      ...state,
      listOfObservations: action.data
    }
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  listOfObservations: []
}

export default function selectReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
