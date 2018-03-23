import { connect } from 'react-redux'
import ViewTrials from './component/ViewTrials'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(ViewTrials)
