import { connect } from 'react-redux'
import SelectObservationComponent from '../components/SelectObservation'
import { getObservations } from './../modules/observation'
import { getViewTrials, clearTrialList } from '../../../routes/ViewTrials/modules/view_trials'

const mapDispatchToProps = {
  getObservations,
  getViewTrials,
  clearTrialList
}

const mapStateToProps = (state) => ({
  listOfObservations: state.select.listOfObservations,
  viewTrials: state.viewTrials.viewTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectObservationComponent)
