import { connect } from 'react-redux'
import ViewTrials from './component/ViewTrials'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  observationForm: state.newobservation.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
