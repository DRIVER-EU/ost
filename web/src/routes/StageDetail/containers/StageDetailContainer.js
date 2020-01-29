import { connect } from 'react-redux'
import StageDetail from '../components/StageDetail'
import { getStageById, updateStage, removeStage } from '../modules/stagedetail'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'

const mapDispatchToProps = {
  getStageById,
  updateStage,
  removeStage,
  getTrialDetail
}

const mapStateToProps = state => {
  return {
    stageId: state.stageDetail.id,
    stageName: state.stageDetail.stageName,
    questions: state.stageDetail.questions,
    trialName: state.trialDetail.trialName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(StageDetail)
