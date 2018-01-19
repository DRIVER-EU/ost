export default (store) => ({
  path : 'trials',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Page = require('./containers/TrialsContainer').default
     //   const reducer = require('./modules/search').default
       // injectReducer(store, { key: 'search', reducer })
      cb(null, Page)
    }, 'search')
  }
})

