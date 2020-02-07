import { connect } from 'react-redux'
import QuestionDetailView from '../components/QuestionDetail'
import { getQuestion, updateQuestion, removeQuestion } from '../../QuestionSet/modules/questiondetail'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { getRoleById } from '../../RoleDetail/modules/roledetail'

const mapDispatchToProps = {
  getQuestion,
  updateQuestion,
  removeQuestion,
  getRoleById,
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
    stageName: state.roleDetail.roleName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(QuestionDetailView)
