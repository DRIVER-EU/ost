import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/role/:id_role/question/:id_question',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/QuestionDetailContainer').default
      const reducer = require('./../QuestionSet/modules/questiondetail').default
      injectReducer(store, { key: 'questionDetail', reducer })
      cb(null, Page)
    }, 'questionDetail')
  }
})
