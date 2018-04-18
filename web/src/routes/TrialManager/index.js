import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'trial-manager',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/TrialManagerContainer').default
      const reducer = require('./modules/trialmanager').default
      injectReducer(store, { key: 'trialManager', reducer })
      cb(null, Page)
    }, 'trialManager')
  }
})

