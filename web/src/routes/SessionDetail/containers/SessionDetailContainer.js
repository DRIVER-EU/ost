import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import SessionDetailView from '../components/SessionDetail'
import { getSessionById, updateSession, removeSession, getObservations } from './../modules/sessiondetail'

const mapDispatchToProps = {
  getTrialDetail,
  getSessionById,
  updateSession,
  removeSession,
  getObservations
}

const mapStateToProps = state => {
  return {
    trialName: state.trialDetail.trialName,
    sessionId: state.sessionDetail.id,
    sessionName: state.sessionDetail.sessionName,
    status: state.sessionDetail.status,
    stageId: state.sessionDetail.stageId,
    stageName: state.sessionDetail.stageName,
    stages: state.sessionDetail.stages,
    userRoles: state.sessionDetail.userRoles,
    openRemoveInfoDialog: state.sessionDetail.openRemoveInfoDialog
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(SessionDetailView)
