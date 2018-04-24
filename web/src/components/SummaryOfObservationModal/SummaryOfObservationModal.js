import { connect } from 'react-redux'
import SummaryOfObservationModal from './component/SummaryOfObservationModal'
import { getSchema } from './../../routes/NewObservation/modules/newobservation'

const mapDispatchToProps = {
  getSchema
}

const mapStateToProps = (state) => ({
  observationForm: state.newobservation.observationForm
})

export default connect(mapStateToProps, mapDispatchToProps)(SummaryOfObservationModal)
