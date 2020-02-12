import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : '/active-sessions',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/ActiveSessionsContainer').default
      const reducer = require('./modules/activesessions').default
      injectReducer(store, { key: 'activeSessions', reducer })
      cb(null, Page)
    }, 'activeSessions')
  }
})
