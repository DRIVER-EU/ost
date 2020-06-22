import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { logIn, logOut } from '../../routes/Login/modules/login'
import './User.scss'
import { toastr } from 'react-redux-toastr'

const toastrOptions = {
  timeOut: 3000
}
class UserComponent extends Component {
  constructor (props) {
    super()
  }

  static propTypes = {
    user: PropTypes.any,
    keycloak: PropTypes.object
  }

  render () {
    return (
      <div className='btns__wrapper'>
        <span style={{ marginRight: 10 }}>{this.props.user.login}</span>
        {this.props.keycloak !== null && <button
          onClick={() => navigator.onLine && this.props.keycloak !== null
            ? this.props.keycloak.onAuthLogout()
            : toastr.warning('Offline mode', 'You can not log out in offline mode', toastrOptions)}
          className='menu__btn'>
            Log out
        </button>}
      </div>
    )
  }
}

const mapDispatchToProps = {
  logIn,
  logOut
}

const mapStateToProps = (state) => ({
  user: state.login.user
})

export default connect(mapStateToProps, mapDispatchToProps)(UserComponent)
