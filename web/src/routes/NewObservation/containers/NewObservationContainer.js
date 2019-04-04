import { connect } from 'react-redux'
import NewObservation from '../components/NewObservation'
import { getSchema, sendObservation, getTrialTime } from './../modules/newobservation'

const mapDispatchToProps = {
  getSchema,
  sendObservation,
  getTrialTime
}

const mapStateToProps = (state) => ({
  observationForm: state.newobservation.observationForm,
  observation: state.newobservation.observation,
  trialTime: state.newobservation.trialTime
})

export default connect(mapStateToProps, mapDispatchToProps)(NewObservation)
