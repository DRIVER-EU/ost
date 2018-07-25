// ------------------------------------
// Constants
// ------------------------------------
// import axios from 'axios'
// export let origin = window.location.hostname
// if (origin === 'localhost') {
//   origin = '192.168.1.11'
// }

// ------------------------------------
// Actions
// ------------------------------------
export const actions = {}

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
export default function aboutReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
