import { connect } from 'react-redux'
import Menu from './component/Menu'

const mapDispatchToProps = {}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(Menu)
