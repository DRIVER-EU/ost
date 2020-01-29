import { connect } from 'react-redux'
import NewQuestionView from '../components/NewQuestion'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { addNewQuestion } from './../modules/newquestion'
import { getStageById } from '../../StageDetail/modules/stagedetail'

const mapDispatchToProps = {
  addNewQuestion,
  getStageById,
  getTrialDetail
}

const mapStateToProps = state => {
  return {
    newQuestionDetail: state.newQuestion.newQuestionDetail,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewQuestionView)
