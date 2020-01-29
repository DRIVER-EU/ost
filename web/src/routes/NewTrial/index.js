import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'new-trial',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewTrialContainer').default
      const reducer = require('./modules/newtrial').default
      injectReducer(store, { key: 'newTrialDetail', reducer })

      cb(null, Page)
    }, 'newTrialDetail')
  }
})
