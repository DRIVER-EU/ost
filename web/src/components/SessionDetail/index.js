import { connect } from 'react-redux'
import SessionDetail from '../../routes/SessionDetail'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(SessionDetail)
