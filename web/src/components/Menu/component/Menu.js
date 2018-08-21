import React, { Component } from 'react'
import { IndexLink } from 'react-router'
import PropTypes from 'prop-types'
import AppBar from 'material-ui/AppBar'
import { List, ListItem } from 'material-ui/List'
import UserComponent from '../../Bar/User'
import './Menu.scss'

class Menu extends Component {
  constructor (props) {
    super()
    this.state = {}
  }

  static propTypes = {
    role: PropTypes.string,
    isLoggedIn: PropTypes.bool
  }

  render () {
    return (
      <div>
        <AppBar
          zDepth={3}
          style={{ backgroundColor: 'white' }}
          iconElementLeft={
            <div style={{ display: 'flex' }}>
              <img className='img-responsive pull-left logo' src='/images/driver-mini-logo.png' />
              <span className='driver-title'>
                Observer Support Tool
              </span>
            </div>
          }
          iconElementRight={
            this.props.isLoggedIn &&
            <List style={{ display: 'flex' }}>
              <ListItem
                primaryText='Trials'
                style={{ color: '#00497E' }}
                containerElement={<IndexLink
                  to={this.props.role === 'ROLE_ADMIN' ? '/trial-manager' : '/trials'}
                  activeClassName='route--active' />} />
              <UserComponent activeClassName='route--active' />
            </List>
          }
  />
      </div>
    )
  }
}

export default Menu
