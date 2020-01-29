import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/session/:id_session',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/SessionDetailContainer').default
      const reducer = require('./modules/sessiondetail').default
      injectReducer(store, { key: 'sessionDetail', reducer })
      cb(null, Page)
    }, 'sessionDetail')
  }
})
