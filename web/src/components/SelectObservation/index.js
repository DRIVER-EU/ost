import { connect } from 'react-redux'
import SelectObservation from './component/SelectObservation'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectObservation)
