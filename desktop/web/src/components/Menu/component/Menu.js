import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { IndexLink } from 'react-router'
import { Nav, Navbar } from 'react-bootstrap'
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
        <Navbar fluid bsStyle='pills' className='nav nav-pills menu'>
          <Navbar.Header>
            <img className='img-responsive pull-left logo' src='/images/driver-mini-logo.png' />
            <Navbar.Toggle />
          </Navbar.Header>
          <Navbar.Collapse>
            <Nav pullRight>
              <li><IndexLink to='/' activeClassName='active'>Home</IndexLink></li>
              <li><IndexLink to='trials' activeClassName=''>Trials</IndexLink></li>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </div>
    )
  }
}

export default MenuLeft
