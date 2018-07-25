import { connect } from 'react-redux'
import SelectObservation from './component/SelectObservation'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name,
  viewTrials: state.viewTrials.viewTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectObservation)
