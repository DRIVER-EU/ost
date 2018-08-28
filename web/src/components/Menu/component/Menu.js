import React, { Component } from 'react'
import PropTypes from 'prop-types'
import AppBar from 'material-ui/AppBar'
import { List } from 'material-ui/List'
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
          style={{ backgroundColor: 'white', height: 74 }}
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
              <UserComponent role={this.props.role} activeClassName='route--active' />
            </List>
          }
  />
      </div>
    )
  }
}

export default Menu
