import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import RoleDetailView from '../components/RoleDetail'
import { getRoleById, removeRole, updateRole } from '../modules/roledetail'

const mapDispatchToProps = {
  getTrialDetail,
  getRoleById,
  removeRole,
  updateRole
}

const mapStateToProps = state => {
  return {
    trialName: state.trialDetail.trialName,
    roleId: state.roleDetail.id,
    roleName: state.roleDetail.roleName,
    roleType: state.roleDetail.roleType,
    questions: state.roleDetail.questions
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(RoleDetailView)
