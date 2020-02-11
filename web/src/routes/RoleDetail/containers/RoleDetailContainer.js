import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import { getUsersList,
  addUser,
  removeUser } from '../../SessionDetail/modules/sessiondetail'
import RoleDetailView from '../components/RoleDetail'
import { getRoleById, removeRole, updateRole } from '../modules/roledetail'

const mapDispatchToProps = {
  getTrialDetail,
  getRoleById,
  removeRole,
  updateRole,
  getUsersList,
  addUser,
  removeUser
}

const mapStateToProps = state => {
  return {
    trialName: state.trialDetail.trialName,
    roleSet: state.trialDetail.roleSet,
    roleId: state.roleDetail.id,
    roleName: state.roleDetail.roleName,
    roleType: state.roleDetail.roleType,
    questions: state.roleDetail.questions,
    userRoles: state.roleDetail.userRoles,
    usersList: state.sessionDetail.usersList

  }
}

export default connect(mapStateToProps, mapDispatchToProps)(RoleDetailView)
