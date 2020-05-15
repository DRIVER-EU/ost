import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './Login.scss'
import RaisedButton from 'material-ui/RaisedButton'

const styles = {
  checkbox: {
    marginTop: 15,
    marginBottom: 15
  },
  signIn: {
    marginTop: 15,
    display: 'block'
  }
}

class LoginPanelContent extends Component {
  static propTypes = {
    keycloak: PropTypes.object
  }

  render () {
    return (
      <div className='pages-flex'>
        <div className='login-box'>
          <p className='singin-title'>Log in</p>
          <RaisedButton
            label='Log in'
            style={styles.signIn}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            onClick={() => this.props.keycloak.login()}
            disabled={this.props.keycloak === null} />
        </div>
      </div>
    )
  }
}

export default LoginPanelContent
