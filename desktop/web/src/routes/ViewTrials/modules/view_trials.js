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

export const GET_VIEW_TRIALS = 'GET_VIEW_TRIALS'
// ------------------------------------
// Actions
// ------------------------------------

export const getViewTrialsAction = (data = null) => {
  return {
    type: GET_VIEW_TRIALS,
    data: data
  }
}

export const actions = {
  getViewTrials
}

export const getViewTrials = () => {
  return (dispatch) => {
    dispatch(getViewTrialsAction({
      total: 6,
      data: [
        {
          id: 1,
          date: '09/03/2018 11:18:18',
          title: 'Lorem ipsum dolor sit amet',
          description: `At vero eos et accusamus et iusto odio dignissimos
          ducimus qui blanditiis praesentium voluptatum deleniti
          atque corrupti quos dolores et quas molestias excepturi sint 
          occaecati cupiditate non provident, similique sunt in culpa qui
          officia deserunt mollitia animi, id est laborum et dolorum fuga.`
        },
        {
          id: 2,
          date: '09/03/2018 11:17:18',
          title: 'Sed ut perspiciatis unde omnis iste natus error',
          description: `Sed ut perspiciatis unde omnis iste natus error
          sit voluptatem accusantium doloremque laudantium,
          totam rem aperiam, eaque ipsa quae ab illo inventore veritatis
          et quasi architecto beatae vitae dicta sunt explicabo.`
        },
        {
          id: 3,
          date: '09/03/2018 11:16:17',
          title: 'At vero eos et accusamus et iusto odio dignissimos',
          description: `At vero eos et accusamus et iusto odio dignissimos
          ducimus qui blanditiis praesentium voluptatum deleniti
          atque corrupti quos dolores et quas molestias excepturi sint 
          occaecati cupiditate non provident, similique sunt in culpa qui
          officia deserunt mollitia animi, id est laborum et dolorum fuga.`
        }
      ] }
    ))

    // return new Promise((resolve) => {
    //   axios.get(`http://${origin}/api/anonymous/trials`, getHeaders())
    //    .then((response) => {
    //      dispatch(getViewTrialsAction(response.data))
    //      resolve()
    //    })
    //    .catch((error) => {
    //      errorHandle(error)
    //      resolve()
    //    })
    // })
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
  }
}
// ------------------------------------
// Reducer
// ------------------------------------
const initialState = {
  viewTrials: []
}

export default function viewTrialsReducer (state = initialState, action) {
  const handler = ACTION_HANDLERS[action.type]

  return handler ? handler(state, action) : state
}
