import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/stage/:id_stage/question/:id_question',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/QuestionDetailContainer').default
      const reducer = require('./modules/questiondetail').default
      injectReducer(store, { key: 'questionDetail', reducer })
      cb(null, Page)
    }, 'questionDetail')
  }
})
