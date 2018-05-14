import { connect } from 'react-redux'
import ViewTrials from '../components/ViewTrials'
import { getViewTrials, getTrialSession } from './../modules/view_trials'

const mapDispatchToProps = {
  getViewTrials,
  getTrialSession
}

const mapStateToProps = (state) => ({
  viewTrials: state.viewTrials.viewTrials,
  trialSession: state.viewTrials.trialSession
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
