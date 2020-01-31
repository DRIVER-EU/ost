import { connect } from 'react-redux'
import QuestionView from '../components/Question'
import { getQuestionDetail, updateQuestion, removeQuestionDetail, addOption, removeOption } from '../modules/question'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { getQuestion } from '../../QuestionSet/modules/questiondetail'
import { getStageById } from '../../StageDetail/modules/stagedetail'

const mapDispatchToProps = {
  getQuestionDetail,
  updateQuestion,
  removeQuestionDetail,
  getStageById,
  getTrialDetail,
  getQuestion,
  addOption,
  removeOption

}

const mapStateToProps = state => {
  return {
    questionDetailId: state.question.id,
    questionName: state.question.questionName,
    description: state.question.description,
    position: state.question.position,
    commented: state.question.commented,
    required: state.question.required,
    option: state.question.option,
    optionId: state.question.optionId,
    optionName: state.question.optionName,
    optionPosition: state.question.optionPosition,
    answerType: state.question.answerType,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName,
    questionSetName: state.questionDetail.questionName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(QuestionView)
