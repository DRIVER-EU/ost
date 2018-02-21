import { connect } from 'react-redux'
import Trials from '../components/Trials'
import getObservation from '../modules/trials'

const mapDispatchToProps = {
  getObservation
}

const mapStateToProps = (state) => ({
  observation: state.trials.observation
})

export default connect(mapStateToProps, mapDispatchToProps)(Trials)
