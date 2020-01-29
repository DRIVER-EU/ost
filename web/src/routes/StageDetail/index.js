import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/stage/:id_stage',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/StageDetailContainer').default
      const reducer = require('./modules/stagedetail').default
      injectReducer(store, { key: 'stageDetail', reducer })
      cb(null, Page)
    }, 'stageDetail')
  }
})

