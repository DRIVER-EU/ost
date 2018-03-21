import { connect } from 'react-redux'
import Bar from './Bar'
import { logOut } from '../../routes/Login/modules/login.js'

const mapDispatchToProps = {
  logOut
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(Bar)
