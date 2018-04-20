import { connect } from 'react-redux'
import TrialManager from '../components/TrialManager'
import { getTrialManager } from './../modules/trialmanager'

const mapDispatchToProps = {
  getTrialManager
}

const mapStateToProps = (state) => ({
  listOfTrialsManager: state.trialManager.listOfTrialsManager
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialManager)
