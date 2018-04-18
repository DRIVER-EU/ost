// ------------------------------------
// Constants
// ------------------------------------
// export let origin = window.location.hostname
// if (origin === 'localhost' || origin === 'dev.itti.com.pl') {
//   origin = 'dev.itti.com.pl:8009'
// }

// ------------------------------------
// Actions
// ------------------------------------

// ------------------------------------
// Action Handlers
// ------------------------------------
const ACTION_HANDLERS = {}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {}

export default function trialManagerReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
