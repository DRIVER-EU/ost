
import TrialManager from './components/AdminHome'

export default (store) => ({
  path : 'admin-home',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      cb(null, TrialManager)
    }, 'admin-home')
  }
})

