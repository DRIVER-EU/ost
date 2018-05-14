import { connect } from 'react-redux'
import AdminTrials from './component/AdminTrials'

const mapDispatchToProps = {
}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(AdminTrials)
