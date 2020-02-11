import { connect } from 'react-redux'
import NewQuestionView from '../components/NewQuestion'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { addNewQuestion } from './../../NewQuestionSet/modules/newquestion'
import { getRoleById } from '../../RoleDetail/modules/roledetail'

const mapDispatchToProps = {
  addNewQuestion,
  getRoleById,
  getTrialDetail
}

const mapStateToProps = state => {
  return {
    newQuestionDetail: state.newQuestion.newQuestionDetail,
    trialName: state.trialDetail.trialName,
    stageName: state.roleDetail.roleName
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewQuestionView)
