import { connect } from 'react-redux'
import { getTrialDetail, updateTrial, removeTrial } from './../modules/trialdetail'
import TrialDetailWrapper from '../components/TrialDetail'

const mapDispatchToProps = {
  getTrialDetail,
  updateTrial,
  removeTrial
}

const mapStateToProps = state => ({
  trialId: state.trialDetail.trialId,
  trialName: state.trialDetail.trialName,
  trialDescription: state.trialDetail.trialDescription,
  lastTrialStage: state.trialDetail.lastTrialStage,
  archived: state.trialDetail.archived,
  stageSet: state.trialDetail.stageSet,
  sessionSet: state.trialDetail.sessionSet,
  roleSet: state.trialDetail.roleSet
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialDetailWrapper)
