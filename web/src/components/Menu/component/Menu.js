import React, { Component } from 'react'
import PropTypes from 'prop-types'
import AppBar from 'material-ui/AppBar'
import { List } from 'material-ui/List'
import UserComponent from '../../Bar/User'
import './Menu.scss'
import browserHistory from 'react-router/lib/browserHistory'

class Menu extends Component {
  constructor () {
    super()
    this.state = {
      version: ''
    }
  }

  static propTypes = {
    role: PropTypes.string,
    isLoggedIn: PropTypes.bool,
    keycloak: PropTypes.object
  }
  componentWillMount () {
    fetch('/version.txt')
    .then(response => response.text())
      .then(data => this.setState({ version: data }))
  }

  render () {
    return (
      <div>
        <AppBar
          zDepth={3}
          style={{ backgroundColor: 'white', height: 74 }}
          iconElementLeft={
            this.props.isLoggedIn &&
            <div style={{ display: 'flex' }} className='menu__info'>
              <a
                onClick={() => browserHistory.push('/')}
                style={{ display: 'flex', alignItems: 'center', cursor: 'pointer' }}>
                <img className='img-responsive pull-left logo' src='/images/ost.png' />
                <span className='driver-title'>
                  Observer Support Tool
                </span>
              </a>
              <p className='version__text'>v.{this.state.version}</p>
            </div>
          }
          iconElementRight={
            this.props.isLoggedIn &&
            <List style={{ display: 'flex', height: '100%', alignItems: 'center' }}>
              <UserComponent role={this.props.role} keycloak={this.props.keycloak} activeClassName='route--active' />
            </List>
          }
  />
      </div>
    )
  }
}

export default Menu
