import { connect } from 'react-redux'
import HomeDesktop from './component/HomeDesktop'
import { logIn } from './../../routes/Login/modules/login'

const mapDispatchToProps = {
  logIn
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(HomeDesktop)

