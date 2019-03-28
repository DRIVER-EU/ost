import { connect } from 'react-redux'
import Login from '../components/Login'
import { logIn } from './../modules/login'

const mapDispatchToProps = {
  logIn
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
