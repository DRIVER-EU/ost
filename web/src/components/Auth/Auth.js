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
        '/login'
      ]
    }
  }
  static propTypes = {
    checkLogin: PropTypes.func,
    isLoggedIn: PropTypes.any
  }

  isPublicLocation () {
    let isPublic = false
    this.state.publicPaths.forEach(function (object) {
      if (
      window.location.pathname === object) {
        isPublic = true
      }
    })
    return isPublic
  }

  componentWillMount () {
    this.props.checkLogin()
    browserHistory.listen(location => {
      if (location.pathname !== '/login') {
        if (!this.props.isLoggedIn && !this.isPublicLocation()) {
          window.location.replace('/login')
        }
      }
    })
    if (!this.props.isLoggedIn && !this.isPublicLocation()) {
      browserHistory.push('/login')
    }
  }

  componentWillReceiveProps (nextProps) {
    if (!nextProps.isLoggedIn && !this.isPublicLocation()) {
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
