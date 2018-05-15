import { connect } from 'react-redux'
import ViewTrials from '../components/ViewTrials'
import { getViewTrials, getTrialSession, getTrials } from './../modules/view_trials'
import { downloadFile, sendObservation } from './../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getViewTrials,
  getTrialSession,
  getTrials,
  downloadFile,
  sendObservation
}

const mapStateToProps = (state) => ({
  viewTrials: state.viewTrials.viewTrials,
  trialSession: state.viewTrials.trialSession,
  listOfTrials: state.viewTrials.listOfTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
