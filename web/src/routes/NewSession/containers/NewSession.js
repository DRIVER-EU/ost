import { connect } from 'react-redux'
import NewSession from '../components/NewSession'
import { getStages, getRoles } from './../../AdminTrials/modules/admin_trials'
import { newSession } from './../../NewSession/modules/newsession'

const mapDispatchToProps = {
  getStages,
  getRoles,
  newSession
}

const mapStateToProps = (state) => ({
  rolesList: state.adminTrials.rolesList,
  stagesList: state.adminTrials.stagesList,
  session: state.newsession.session

})

export default connect(mapStateToProps, mapDispatchToProps)(NewSession)
