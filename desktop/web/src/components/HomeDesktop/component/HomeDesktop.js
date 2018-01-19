import React, { Component } from 'react'

const styles = {
  logoPosition: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  }
}

class HomeDesktop extends Component {
  constructor (props) {
    super(props)
    this.state = { }
  }

  static propTypes = { }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div style={styles.logoPosition}>
            <img className='img-responsive driver-logo' src='/images/driver-logo.png' />
          </div>
        </div>
      </div>
    )
  }
}

export default HomeDesktop
