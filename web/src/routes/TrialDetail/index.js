import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'trial-detail/:id_trial',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/TrialDetailContainer').default
      const reducer = require('./modules/trialdetail').default
      injectReducer(store, { key: 'trialDetail', reducer })

      cb(null, Page)
    }, 'trialDetail')
  }
})
