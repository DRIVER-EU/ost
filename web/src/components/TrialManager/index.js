import { connect } from 'react-redux'
import TrialManager from './component/TrialManager'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialManager)
