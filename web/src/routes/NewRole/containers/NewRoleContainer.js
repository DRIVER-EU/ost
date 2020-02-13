import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import NewRoleView from '../components/NewRole'
import { getUsersList } from '../../SessionDetail/modules/sessiondetail'
import { addNewRole } from '../modules/newrole'

const mapDispatchToProps = {
  getTrialDetail,
  addNewRole,
  getUsersList
}

const mapStateToProps = state => {
  return {
    trialName: state.trialDetail.trialName,
    roleId: state.newRole.id,
    roleName: state.newRole.roleName,
    roleType: state.newRole.roleType,
    questions: state.newRole.questions,
    roleSet: state.trialDetail.roleSet,
    usersList: state.sessionDetail.usersList
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewRoleView)
