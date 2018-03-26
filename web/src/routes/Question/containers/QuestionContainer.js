import { connect } from 'react-redux'
import Question from '../components/Question'
import { getViewSchema } from './../modules/question'

const mapDispatchToProps = {
  getViewSchema
}

const mapStateToProps = (state) => ({
  viewSchema: state.question.viewSchema
})

export default connect(mapStateToProps, mapDispatchToProps)(Question)
