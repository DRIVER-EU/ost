import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':trial_id/admin-trials/:id',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/AdminTrialsContainer').default
      const reducer = require('./modules/admin_trials').default
      injectReducer(store, { key: 'adminTrials', reducer })
      cb(null, Page)
    }, 'admin_trials')
  }
})

