import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { IndexLink } from 'react-router'
import AppBar from 'material-ui/AppBar'
import { List, ListItem } from 'material-ui/List'
import UserComponent from '../../Bar/User'

import './Menu.scss'

class MenuLeft extends Component {
  constructor (props) {
    super()
    this.state = {
      openToast: false,
      toastDescription: '',
      isLoggedIn: true,
      user: 'Andrew',
      role: 'user'
    }
  }

  static propTypes = {
    user: PropTypes.any
  }

  render () {
    return (
      <div>
        <AppBar
          zDepth={3}
          style={{ backgroundColor: 'white' }}
          iconElementLeft={<img className='img-responsive pull-left logo' src='/images/driver-mini-logo.png' />}
          iconElementRight={
            <List style={{ display: 'flex' }}>
              <ListItem
                primaryText='Home'
                style={{ color: '#00497E' }}
                containerElement={<IndexLink to='/' activeClassName='route--active' />} />
              <ListItem
                primaryText='Trials'
                style={{ color: '#00497E' }}
                containerElement={<IndexLink to='/trials' activeClassName='route--active' />} />
              <UserComponent activeClassName='route--active' />
            </List>
          }
  />
      </div>
    )
  }
}

export default MenuLeft
