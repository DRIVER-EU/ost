import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id/select-observation',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/SelectObservationContainer').default
      const reducer = require('./modules/observation').default
      injectReducer(store, { key: 'select', reducer })
      cb(null, Page)
    }, 'select')
  }
})

