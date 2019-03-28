import { connect } from 'react-redux'
import ViewTrials from '../components/ViewTrials'
import { getViewTrials, getTrialSession, getTrials, clearTrialList,
  removeAnswer, editComment } from './../modules/view_trials'
import { downloadFile, sendObservation } from './../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getViewTrials,
  getTrialSession,
  getTrials,
  downloadFile,
  sendObservation,
  clearTrialList,
  removeAnswer,
  editComment
}

const mapStateToProps = (state) => ({
  viewTrials: state.viewTrials.viewTrials,
  trialSession: state.viewTrials.trialSession,
  listOfTrials: state.viewTrials.listOfTrials,
  editedComment: state.viewTrials.editedComment
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
