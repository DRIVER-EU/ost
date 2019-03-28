import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id/new-observation/:id_observation',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewObservationContainer').default
      const reducer = require('./modules/newobservation').default
      injectReducer(store, { key: 'newobservation', reducer })
      cb(null, Page)
    }, 'newobservation')
  }
})

