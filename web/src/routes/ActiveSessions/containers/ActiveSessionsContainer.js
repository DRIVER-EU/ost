import { connect } from 'react-redux'
import {
  getSessionDetail,
  getActiveSessions
} from './../modules/activesessions'
import ActiveSessionsView from '../components/ActiveSessions'

const mapDispatchToProps = {
  getSessionDetail,
  getActiveSessions
}

const mapStateToProps = state => {
  return {
    activeSessionsList: state.activeSessions.activeSessionsList,
    sessionDetail: state.activeSessions.sessionDetail
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ActiveSessionsView)
