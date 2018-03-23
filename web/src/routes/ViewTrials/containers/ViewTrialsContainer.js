import { connect } from 'react-redux'
import ViewTrials from '../components/ViewTrials'
import { getViewTrials } from './../modules/view_trials'

const mapDispatchToProps = {
  getViewTrials
}

const mapStateToProps = (state) => ({
  viewTrials: state.viewTrials.viewTrials
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
