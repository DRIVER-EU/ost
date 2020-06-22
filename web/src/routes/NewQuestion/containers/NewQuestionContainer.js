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
    questionDetailId: state.newQuestionDetail.id,
    description: state.newQuestionDetail.description,
    position: state.newQuestionDetail.position,
    commented: state.newQuestionDetail.commented,
    required: state.newQuestionDetail.required,
    option: state.newQuestionDetail.option,
    answerType: state.newQuestionDetail.answerType,
    trialName: state.trialDetail.trialName,
    stageName: state.stageDetail.stageName,
    questionSetName: state.questionDetail.questionName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewQuestionView)
