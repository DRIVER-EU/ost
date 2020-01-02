import { connect } from 'react-redux'
import TrialManager from '../components/TrialManager'
import { getTrialManager, getListOfTrials, importFile } from './../modules/trialmanager'

const mapDispatchToProps = {
  getTrialManager,
  getListOfTrials,
  importFile
}

const mapStateToProps = (state) => ({
  listOfTrialsManager: state.trialManager.listOfTrialsManager,
  listOfTrials: state.trialManager.listOfTrials2
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialManager)
