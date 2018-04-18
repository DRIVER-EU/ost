import { connect } from 'react-redux'
import Question from '../components/Question'
import { getSchema } from './../modules/question'

const mapDispatchToProps = {
  getSchema
}

const mapStateToProps = (state) => ({
  observationForm: state.question.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
