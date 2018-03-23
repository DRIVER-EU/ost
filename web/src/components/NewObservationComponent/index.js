import { connect } from 'react-redux'
import NewObservationComponent from './component/NewObservationComponent'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(NewObservationComponent)
