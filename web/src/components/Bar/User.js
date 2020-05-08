import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { logIn, logOut } from '../../routes/Login/modules/login'
import './User.scss'
// import { browserHistory } from 'react-router'

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
        <button className='menu__btn'>{this.props.user.login}</button>
        <button
          disabled={this.props.keycloak === null}
          onClick={() => this.props.keycloak.onAuthLogout()}
          className='menu__btn'>
            Log out
        </button>
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
