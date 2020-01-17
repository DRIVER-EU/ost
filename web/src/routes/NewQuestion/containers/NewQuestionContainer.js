import { connect } from 'react-redux'
import NewQuestionView from '../components/NewQuestion'
import { getQuestion, updateQuestion, removeQuestion } from '../modules/newquestion'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { getStageById } from '../../StageDetail/modules/stagedetail'

const mapDispatchToProps = {
  getQuestion,
  updateQuestion,
  removeQuestion,
  getStageById,
  getTrialDetail
}

const mapStateToProps = state => {
  return {
    // questionDetailId: state.question.id,
    // questionName: state.question.questionName,
    // description: state.question.description,
    // position: state.question.position,
    // questionsDetailList: state.question.questions,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewQuestionView)
