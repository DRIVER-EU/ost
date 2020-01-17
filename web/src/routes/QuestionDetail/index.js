import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/stage/:id_stage/question/:id_question/question-detail/:id_questdetail',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/QuestionContainer').default
      const reducer = require('./modules/question').default
      injectReducer(store, { key: 'question', reducer })
      cb(null, Page)
    }, 'question')
  }
})
