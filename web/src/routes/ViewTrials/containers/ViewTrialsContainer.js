import { connect } from 'react-redux'
import ViewTrials from '../components/ViewTrials'
import { getViewTrials, getTrialSession, getTrials } from './../modules/view_trials'
import { downloadFile } from './../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getViewTrials,
  getTrialSession,
  getTrials,
  downloadFile
}

const mapStateToProps = (state) => ({
  viewTrials: state.viewTrials.viewTrials,
  trialSession: state.viewTrials.trialSession,
  listOfTrials: state.viewTrials.listOfTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
