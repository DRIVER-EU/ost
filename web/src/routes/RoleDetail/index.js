import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/role/:id_role',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/RoleDetailContainer').default
      const reducer = require('./modules/roledetail').default
      injectReducer(store, { key: 'roleDetail', reducer })
      cb(null, Page)
    }, 'roleDetail')
  }
})
