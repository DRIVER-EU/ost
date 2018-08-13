import { connect } from 'react-redux'
import NewSession from '../components/NewSession'
import { getStages, getRoles } from './../../AdminTrials/modules/admin_trials'

const mapDispatchToProps = {
  getStages,
  getRoles
}

const mapStateToProps = (state) => ({
  rolesList: state.adminTrials.rolesList,
  stagesList: state.adminTrials.stagesList
})

export default connect(mapStateToProps, mapDispatchToProps)(NewSession)
