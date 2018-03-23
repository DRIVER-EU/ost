import { connect } from 'react-redux'
import Bar from './Bar'

const mapDispatchToProps = {
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn
})

export default connect(mapStateToProps, mapDispatchToProps)(Bar)
