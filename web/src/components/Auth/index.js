import { connect } from 'react-redux'
import Auth from './Auth'
import { checkLogin } from './../../routes/Login/modules/login'

const mapDispatchToProps = {
  checkLogin
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(Auth)
