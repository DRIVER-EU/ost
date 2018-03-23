import React, { Component } from 'react'
import './HomeView.scss'
import HomeDesktop from '../../../components/HomeDesktop'

class HomeView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  render () {
    return (
      <div className='background-home'>
        <HomeDesktop />
      </div>
    )
  }
}

export default HomeView
