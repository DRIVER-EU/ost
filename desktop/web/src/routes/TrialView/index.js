import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/TrialContainer').default
      const reducer = require('./modules/trial').default
      injectReducer(store, { key: 'trial', reducer })
      cb(null, Page)
    }, 'trial')
  }
})

