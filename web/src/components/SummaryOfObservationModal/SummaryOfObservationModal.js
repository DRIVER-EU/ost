import { connect } from 'react-redux'
import SummaryOfObservationModal from './component/SummaryOfObservationModal'
import { getSchemaView } from './../../routes/Question/modules/question'

const mapDispatchToProps = {
  getSchema: getSchemaView
}

const mapStateToProps = (state) => ({
  observationForm: state.question.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(SummaryOfObservationModal)
