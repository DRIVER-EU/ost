import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/stage/:id_stage/new-question',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewQuestionContainer').default
      const reducer = require('./modules/newquestion').default
      injectReducer(store, { key: 'newQuestion', reducer })
      cb(null, Page)
    }, 'newQuestion')
  }
})
