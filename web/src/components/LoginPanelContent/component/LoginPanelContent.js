import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './Login.scss'
import RaisedButton from 'material-ui/RaisedButton'
import TextField from 'material-ui/TextField'
import { browserHistory } from 'react-router'

const styles = {
  checkbox: {
    marginTop: 15,
    marginBottom: 15
  },
  forgot: {
    marginTop: 15,
    display: 'block'
  }
}

class LoginPanelContent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      name: '',
      password: '',
      nameErrorText: '',
      passwordErrorText: '',
      openDialog: false,
      openPasswordInfo: false
    }
    this.handleChange = this.handleChange.bind(this)
  }

  static propTypes = {
    logIn: PropTypes.func,
    isLoggedIn: PropTypes.bool
  }

  isDisabled (nameArray) {
    for (let name of nameArray) {
      let errorPass = name + 'ErrorText'
      let change = {}
      if (this.state[name] === '') {
        change[errorPass] = 'The field is required.'
      } else {
        change[errorPass] = ''
      }
      this.setState(change)
    }
  }

  handleChange (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }

  _handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      this.props.logIn(this.state.name, this.state.password)
    }
  }

  componentWillMount () {
    let role = localStorage.getItem('driverrole')
    if (this.props.isLoggedIn && role === 'ROLE_ADMIN') {
      browserHistory.push('/trial-manager')
    } else if (this.props.isLoggedIn && role === 'ROLE_USER') {
      browserHistory.push('/trials')
    }
  }

  componentWillReceiveProps (nextProps) {
    let role = localStorage.getItem('driverrole')
    if (nextProps.isLoggedIn && role === 'ROLE_ADMIN') {
      browserHistory.push('/trial-manager')
    } else if (nextProps.isLoggedIn && role === 'ROLE_USER') {
      browserHistory.push('/trials')
    }
  }

  forgot () {
    browserHistory.push('/forgotpassword')
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box pages-flex'>
          <div className='login-box' onKeyPress={this._handleKeyPress}>
            <p className='singin-title'>Sign in</p>

            <TextField
              type='email'
              onChange={this.handleChange.bind(this, 'name')}
              value={this.state.name}
              floatingLabelText='Login'
              fullWidth
              errorText={this.state.nameErrorText !== '' ? this.state.nameErrorText : ''} />

            <TextField
              type='password'
              onChange={this.handleChange.bind(this, 'password')}
              value={this.state.password}
              floatingLabelText='Password'
              fullWidth
              errorText={this.state.passwordErrorText !== '' ? this.state.passwordErrorText : ''} />

            <span
              className='cursor-pointer'
              onClick={this.forgot.bind(this)}>
              Forgot your password?
            </span>

            <RaisedButton
              label='Sign in'
              style={styles.forgot}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              onClick={() => {
                this.isDisabled(['name', 'password'])
                this.props.logIn(this.state.name, this.state.password)
              }} />
          </div>
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state) => ({})

export default connect(mapStateToProps)(LoginPanelContent)
