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

// ------------------------------------
// Actions
// ------------------------------------

export const actions = {
}

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
}

export default function messagesReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
