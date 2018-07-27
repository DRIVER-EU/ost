import { connect } from 'react-redux'
import NewSession from '../components/NewSession'
import { getUsers, getStages, getRoles } from './../../AdminTrials/modules/admin_trials'

const mapDispatchToProps = {
  getUsers,
  getStages,
  getRoles
}
const mapStateToProps = (state) => ({
  usersList: state.adminTrials.usersList,
  rolesList: state.adminTrials.rolesList,
  stagesList: state.adminTrials.stagesList
})
export default connect(mapStateToProps, mapDispatchToProps)(NewSession)
