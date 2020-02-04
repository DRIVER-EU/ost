import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/newrole',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewRoleContainer').default
      const reducer = require('./modules/newrole').default
      injectReducer(store, { key: 'newRole', reducer })
      cb(null, Page)
    }, 'newRole')
  }
})
