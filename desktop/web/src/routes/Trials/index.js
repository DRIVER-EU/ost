import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'admin-trials',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/TrialsContainer').default
      const reducer = require('./modules/trials').default
      injectReducer(store, { key: 'trials', reducer })
      cb(null, Page)
    }, 'trials')
  }
})

