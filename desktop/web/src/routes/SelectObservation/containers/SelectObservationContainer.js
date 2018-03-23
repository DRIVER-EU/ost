import { connect } from 'react-redux'
import SelectObservationComponent from '../components/SelectObservation'
import { getObservations } from './../modules/observation'

const mapDispatchToProps = {
  getObservations
}

const mapStateToProps = (state) => ({
  listOfObservations: state.select.listOfObservations
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectObservationComponent)
