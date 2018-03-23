import { connect } from 'react-redux'
import NewObservation from '../components/NewObservation'
import { getSchema, sendObservation } from './../modules/newobservation'

const mapDispatchToProps = {
  getSchema,
  sendObservation
}

const mapStateToProps = (state) => ({
  questionSchema: state.newobservation.questionSchema,
  observation: state.newobservation.observation
})

export default connect(mapStateToProps, mapDispatchToProps)(NewObservation)
