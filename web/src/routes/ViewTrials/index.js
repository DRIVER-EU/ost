import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/ViewTrialsContainer').default
      const reducer = require('./modules/view_trials').default
      injectReducer(store, { key: 'viewTrials', reducer })
      cb(null, Page)
    }, 'view_trials')
  }
})

