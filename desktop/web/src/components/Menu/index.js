import { connect } from 'react-redux'
import Menu from './component/Menu'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  name: state.menu.name
})

export default connect(mapStateToProps, mapDispatchToProps)(Menu)
