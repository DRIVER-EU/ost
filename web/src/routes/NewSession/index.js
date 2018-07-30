import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id/newsession',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const NewSession = require('./containers/NewSession').default
      const reducer = require('./modules/newsession').default
      injectReducer(store, { key: 'newsession', reducer })
      cb(null, NewSession)
    }, 'newsession')
  }
})
