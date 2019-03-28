import { connect } from 'react-redux'
import SummaryOfObservationModal from './component/SummaryOfObservationModal'
import { getSchema } from './../../routes/NewObservation/modules/newobservation'
import { getSchemaView } from './../../routes/Question/modules/question'

const mapDispatchToProps = {
  getSchema,
  getSchemaView
}

const mapStateToProps = (state) => ({

})

export default connect(mapStateToProps, mapDispatchToProps)(SummaryOfObservationModal)
