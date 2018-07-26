import { connect } from 'react-redux'
import TrialManager from '../components/TrialManager'
import { getTrialManager, getListOfTrials } from './../modules/trialmanager'

const mapDispatchToProps = {
  getTrialManager,
  getListOfTrials
}

const mapStateToProps = (state) => ({
  listOfTrialsManager: state.trialManager.listOfTrialsManager,
  listOfTrials: state.trialManager.listOfTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialManager)
