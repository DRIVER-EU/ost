import { connect } from 'react-redux'
import ActiveSessions from './component/ActiveSessions'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(ActiveSessions)
