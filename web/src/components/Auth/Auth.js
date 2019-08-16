import React, { Component } from 'react'

import PropTypes from 'prop-types'

class Auth extends Component {
  constructor (props) {
    super()
    this.state = {
      publicPaths: [
        '',
        '/'
      ]
    }
  }
  static propTypes = {
    checkLogin: PropTypes.func,
    isLoggedIn: PropTypes.any
  }

  isPublicLocation () {
    let isPublic = false
    this.state.publicPaths.forEach(path => {
      if (
      window.location.pathname === path) {
        isPublic = true
      }
    })
    return isPublic
  }

  componentWillMount () {
    this.props.checkLogin()
    // browserHistory.listen(location => {
    //   if (location.pathname !== '/') {
    //     if (!this.props.isLoggedIn && !this.isPublicLocation()) {
    //       window.location.replace('/')
    //     }
    //   }
    // })
    if (!this.props.isLoggedIn && !this.isPublicLocation()) {
      window.location = '/#/'
    }
  }

  componentWillReceiveProps (nextProps) {
    if (!nextProps.isLoggedIn && !this.isPublicLocation()) {
      window.location = '/#/'
    }
  }

  render () {
    return (
      <div />
    )
  }

}

export default Auth
