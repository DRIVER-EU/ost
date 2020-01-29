import { connect } from 'react-redux'
import NewQuestionView from '../components/NewQuestion'
import { addNewQuestionDetail } from '../modules/newquestion'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { getStageById } from '../../StageDetail/modules/stagedetail'
import { getQuestion } from '../../QuestionSet/modules/questiondetail'

const mapDispatchToProps = {
  addNewQuestionDetail,
  getStageById,
  getTrialDetail,
  getQuestion
}

const mapStateToProps = state => {
  return {
    questionDetailId: state.newQuestion.id,
    questionName: state.newQuestion.questionName,
    description: state.newQuestion.description,
    position: state.newQuestion.position,
    commented: state.newQuestion.commented,
    required: state.newQuestion.required,
    option: state.newQuestion.option,
    answerType: state.newQuestion.answerType,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName,
    questionSetName: state.questionDetail.questionName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewQuestionView)
