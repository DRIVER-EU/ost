import React, { Component } from 'react'

class HomeDesktop extends Component {
  constructor (props) {
    super(props)
    this.state = { }
  }

  static propTypes = { }

  render () {
    return (
      <div className='main-container'>
        <img className='img-responsive driver-logo' src='/images/driver-logo.png' />
      </div>
    )
  }
}

export default HomeDesktop
