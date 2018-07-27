// We only need to import the modules necessary for initial render
import CoreLayout from '../layouts/CoreLayout/CoreLayout'
import Home from './Home'
import Trials from './Trials'
import LoginRoute from './Login'
import Question from './Question'
import ViewTrials from './ViewTrials'
import AdminTrials from './AdminTrials'
import SelectObservationComponent from './SelectObservation'
import NewObservation from './NewObservation'
import TrialManager from './TrialManager'
import NewSession from './NewSession'

/*  Note: Instead of using JSX, we recommend using react-router
    PlainRoute objects to build route definitions.   */

export const createRoutes = (store) => ({
  path        : '/',
  component   : CoreLayout,
  indexRoute  : Home,
  childRoutes : [
    Trials(store),
    LoginRoute(store),
    TrialManager(store),
    AdminTrials(store),
    {
      path: 'trial-manager',
      childRoutes: [
        NewSession(store)
      ]
    },
    {
      path: 'trials',
      childRoutes: [
        ViewTrials(store),
        SelectObservationComponent(store),
        Question(store),
        NewObservation(store)
      ]
    }
  ]
})

/*  Note: childRoutes can be chunked or otherwise loaded programmatically
    using getChildRoutes with the following signature:

    getChildRoutes (location, cb) {
      require.ensure([], (require) => {
        cb(null, [
          // Remove imports!
          require('./Counter').default(store)
        ])
      })
    }

    However, this is not necessary for code-splitting! It simply provides
    an API for async route definitions. Your code splitting should occur
    inside the route `getComponent` function, since it is only invoked
    when the route
    exists and matches.
*/

export default createRoutes
