import { connect } from 'react-redux'
import Question from '../components/Question'
import { getSchemaView } from './../modules/question'

const mapDispatchToProps = {
  getSchemaView
}

const mapStateToProps = (state) => ({
  observationForm: state.question.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
