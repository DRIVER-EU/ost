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
import AdminHome from './AdminHome'
import NewSession from './NewSession'
import StageDetail from './StageDetail'
import TrialDetail from './TrialDetail'
import NewTrial from './NewTrial'
import NewStage from './NewStage'
import QuestionSet from './QuestionSet'
import NewQuestionSet from './NewQuestionSet'
import QuestionDetail from './QuestionDetail'
import NewQuestion from './NewQuestion'
import SessionDetail from './SessionDetail'
import NewSessionDetail from './NewSessionDetail'
import RoleDetail from './RoleDetail'
import NewRole from './NewRole'
import Users from './Users'
import QuestionsSetForRole from './QuestionsSetForRole'
import NewQuestionSetForRole from './NewQuestionSetForRole'
import ActiveSessions from './ActiveSessions'

/*  Note: Instead of using JSX, we recommend using react-router
    PlainRoute objects to build route definitions.   */

export const createRoutes = store => ({
  path: '/',
  component: CoreLayout,
  indexRoute: Home,
  childRoutes: [
    Trials(store),
    LoginRoute(store),
    AdminHome(store),
    TrialManager(store),
    Users(store),
    ActiveSessions(store),
    {
      path: 'trial-manager',
      childRoutes: [
        NewSession(store),
        AdminTrials(store),
        NewTrial(store),
        TrialDetail(store),
        {
          path: 'trial-detail',
          childRoutes: [
            StageDetail(store),
            NewStage(store),
            SessionDetail(store),
            NewSessionDetail(store),
            RoleDetail(store),
            NewRole(store),
            QuestionSet(store),
            NewQuestionSet(store),
            QuestionDetail(store),
            NewQuestion(store),
            QuestionsSetForRole(store),
            NewQuestionSetForRole(store)
          ]
        }
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
