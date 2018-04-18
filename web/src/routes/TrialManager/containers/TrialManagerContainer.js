import { connect } from 'react-redux'
import TrialManager from '../components/TrialManager'
import { getTrials } from './../../Trials/modules/trials'

const mapDispatchToProps = {
  getTrials
}

const mapStateToProps = (state) => ({
  listOfTrials: state.trials.listOfTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialManager)
