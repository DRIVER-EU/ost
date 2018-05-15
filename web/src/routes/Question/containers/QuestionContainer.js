import { connect } from 'react-redux'
import Question from '../components/Question'
import { getSchemaView } from './../modules/question'
import { downloadFile } from '../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getSchemaView,
  downloadFile
}

const mapStateToProps = (state) => ({
  observationForm: state.question.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
