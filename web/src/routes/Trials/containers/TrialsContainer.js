import { connect } from 'react-redux'
import Trials from '../components/Trials'
import { getTrials } from './../modules/trials'

const mapDispatchToProps = {
  getTrials
}

const mapStateToProps = (state) => ({
  listOfTrials: state.trials.listOfTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(Trials)
