import { connect } from 'react-redux'
import { getTrialDetail } from '../../TrialDetail/modules/trialdetail'
import NewRoleView from '../components/NewRole'
import { addNewRole } from '../modules/newrole'

const mapDispatchToProps = {
  getTrialDetail,
  addNewRole
}

const mapStateToProps = state => {
  return {
    trialName: state.trialDetail.trialName,
    roleId: state.newRole.id,
    roleName: state.newRole.roleName,
    roleType: state.newRole.roleType
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NewRoleView)
