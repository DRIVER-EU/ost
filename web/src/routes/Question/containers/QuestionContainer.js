import { connect } from 'react-redux'
import Question from '../components/Question'
import { getSchemaView, resetObservation } from './../modules/question'
import { downloadFile, getTrialTime } from '../../NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getSchemaView,
  downloadFile,
  getTrialTime,
  resetObservation
}

const mapStateToProps = (state) => ({
  observationForm: state.question.observationForm,
  trialTime: state.newobservation.trialTime
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
