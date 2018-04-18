import { connect } from 'react-redux'
import Question from '../components/Question'
import { getSchema } from '../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getSchema
}

const mapStateToProps = (state) => ({
  observationForm: state.newobservation.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
