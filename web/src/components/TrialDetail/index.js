import TrialDetail from './component/TrialDetail'
import { connect } from 'react-redux'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(TrialDetail)
