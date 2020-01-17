import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : ':id_trial/newstage',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/NewStageContainer').default
      const reducer = require('./modules/newstage').default
      injectReducer(store, { key: 'newStageDetail', reducer })
      cb(null, Page)
    }, 'new-trial')
  }
})
