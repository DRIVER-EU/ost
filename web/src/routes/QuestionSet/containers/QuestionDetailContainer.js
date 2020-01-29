import { connect } from 'react-redux'
import QuestionDetailView from '../components/QuestionDetail'
import { getQuestion, updateQuestion, removeQuestion } from '../modules/questiondetail'
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
    questionId: state.questionDetail.id,
    questionName: state.questionDetail.questionName,
    description: state.questionDetail.description,
    position: state.questionDetail.position,
    questionsDetailList: state.questionDetail.questions,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(QuestionDetailView)
