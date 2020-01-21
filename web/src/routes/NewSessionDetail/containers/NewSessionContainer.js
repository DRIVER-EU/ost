import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import NewSessionWrapper from '../components/NewSession'
import { addNewSession } from './../modules/newsession'

const mapDispatchToProps = {
  getTrialDetail,
  addNewSession
}

const mapStateToProps = state => ({
  trialName: state.newSessionDetail.trialName,
  sessionId: state.newSessionDetail.id,
  sessionName: state.newSessionDetail.sessionName,
  status: state.newSessionDetail.status,
  stageId: state.newSessionDetail.stageId,
  stageName: state.newSessionDetail.stageName,
  stages: state.newSessionDetail.stages,
  userRoles: state.newSessionDetail.userRoles
})

export default connect(mapStateToProps, mapDispatchToProps)(NewSessionWrapper)
