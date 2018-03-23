import React, { Component } from 'react'
import './Login.scss'
import PropTypes from 'prop-types'
import LoginPanelContent from '../../../components/LoginPanelContent/component/LoginPanelContent'

class Login extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    logIn: PropTypes.func,
    isLoggedIn: PropTypes.bool
  }

  render () {
    return (
      <div className='main-container'>
        <LoginPanelContent
          isLoggedIn={this.props.isLoggedIn}
          logIn={this.props.logIn}
        />
      </div>
    )
  }
}

export default Login
