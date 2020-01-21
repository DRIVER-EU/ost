import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/newsession',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewSessionContainer').default
      const reducer = require('./modules/newsession').default
      injectReducer(store, { key: 'newSessionDetail', reducer })
      cb(null, Page)
    }, 'newSessionDetail')
  }
})
