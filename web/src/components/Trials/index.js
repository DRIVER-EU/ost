import { connect } from 'react-redux'
import Trials from './component/Trials'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(Trials)
