import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id/question/:id_observation',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/QuestionContainer').default
      const reducer = require('./modules/question').default
      injectReducer(store, { key: 'question', reducer })
      cb(null, Page)
    }, 'question')
  }
})

