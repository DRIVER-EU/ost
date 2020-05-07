import React, { Component } from 'react'
import './HomeView.scss'
import PropTypes from 'prop-types'
import HomeDesktop from '../../../components/HomeDesktop'

class HomeView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    keycloak  : PropTypes.object
  }

  render () {
    return (
      <div className='background-home'>
        <HomeDesktop keycloak={this.props.keycloak} />
      </div>
    )
  }
}

export default HomeView
