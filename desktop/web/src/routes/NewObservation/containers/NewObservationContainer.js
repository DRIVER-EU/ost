import { connect } from 'react-redux'
import NewObservation from '../components/NewObservation'
import { getSchema } from './../modules/newobservation'

const mapDispatchToProps = {
  getSchema
}

const mapStateToProps = (state) => ({
  questionSchema: state.newobservation.questionSchema
})

export default connect(mapStateToProps, mapDispatchToProps)(NewObservation)
