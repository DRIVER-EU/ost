import { connect } from 'react-redux'
import StageDetail from './component/StageDetail'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(StageDetail)
