import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/role/:id_role/new-question',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewQuestionContainer').default
      const reducer = require('./../NewQuestionSet/modules/newquestion').default
      injectReducer(store, { key: 'newQuestion', reducer })
      cb(null, Page)
    }, 'newQuestion')
  }
})
