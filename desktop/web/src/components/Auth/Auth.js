import React, { Component } from 'react'
import { browserHistory } from 'react-router'
import PropTypes from 'prop-types'

class Auth extends Component {
  constructor (props) {
    super()
    this.state = {
      publicPaths: [
        '',
        '/',
        '/login',
        '/trials',
        '/admin-trials'
      ]
    }
  }
  static propTypes = {
    checkLogin: PropTypes.func,
    isLoggedIn: PropTypes.any
  }

  isPublicLocation () {
    this.state.publicPaths.forEach(function (object) {
      if (window.location.pathname.indexOf(object) !== -1) {
        return true
      }
    })
    return false
  }

  componentWillMount () {
    this.props.checkLogin()
    if (!this.props.isLoggedIn && this.isPublicLocation()) {
      browserHistory.push('/login')
    }
  }

  componentWillReceiveProps (nextProps) {
    if (!nextProps.isLoggedIn && this.isPublicLocation()) {
      browserHistory.push('/login')
    }
  }

  render () {
    return (
      <div />
    )
  }

}

export default Auth
