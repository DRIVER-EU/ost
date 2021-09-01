import React, { Component } from 'react'
import PropTypes from 'prop-types'
import LoginPanel from '../../LoginPanelContent/component/LoginPanelContent'

const styles = {
  logoPosition: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white'

  }
}

class HomeDesktop extends Component {
  constructor (props) {
    super(props)
    this.state = { }
  }

  static propTypes = {
    logIn: PropTypes.func,
    isLoggedIn: PropTypes.bool
  }

  render () {
    return (
      <div className='main-container' >
        <div className='pages-box'>
          <div style={styles.logoPosition}>
            <div>
//              <img className='img-responsive driver-logo' src='/images/driver-logo.png' />
             <img className='img-responsive driver-logo' src='/images/ost.png' />
              <LoginPanel
                isLoggedIn={this.props.isLoggedIn}
                logIn={this.props.logIn} />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default HomeDesktop
