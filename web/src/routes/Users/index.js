import UsersManager from './components/UsersManager'

export default (store) => ({
  path : 'users',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      cb(null, UsersManager)
    }, 'users')
  }
})
