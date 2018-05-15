import { connect } from 'react-redux'
import NewObservation from '../components/NewObservation'
import { getSchema, sendObservation, downloadFile } from './../modules/newobservation'

const mapDispatchToProps = {
  getSchema,
  sendObservation,
  downloadFile
}

const mapStateToProps = (state) => ({
  observationForm: state.newobservation.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(NewObservation)
