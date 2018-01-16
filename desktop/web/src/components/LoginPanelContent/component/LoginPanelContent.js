import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './Login.scss'
import { Button, FormGroup, FormControl, Col, Checkbox, ControlLabel, HelpBlock } from 'react-bootstrap'
import { browserHistory } from 'react-router'

const styles = {
  button: {
    width: 100,
    marginTop: '30px'
  },
  form: {
    width: '100%',
    maxWidth: '400px'
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
        change[errorPass] = 'The field is required'
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
    if (this.props.isLoggedIn) {
      browserHistory.push('/')
    }
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.isLoggedIn) {
      browserHistory.push('/')
    }
  }

  forgot () {
    browserHistory.push('/forgotpassword')
  }

  render () {
    return (
      <div style={styles.form} onKeyPress={this._handleKeyPress}>
        <FormGroup controlId='formHorizontalEmail' validationState={this.state.nameErrorText !== '' ? 'error' : ''}>
          <Col componentClass={ControlLabel} >
              Login / Email
            </Col>
          <Col>
            <FormControl type='email'
              onChange={this.handleChange.bind(this, 'name')}
              value={this.state.name}
              placeholder='Email' />
            <HelpBlock>{this.state.nameErrorText}</HelpBlock>
          </Col>
        </FormGroup>

        <FormGroup
          controlId='formHorizontalPassword'
          validationState={this.state.passwordErrorText !== '' ? 'error' : ''}>
          <Col componentClass={ControlLabel}>
              Password
            </Col>
          <Col>
            <FormControl type='password'
              onChange={this.handleChange.bind(this, 'password')}
              value={this.state.password}
              placeholder='Password' />
            <HelpBlock>{this.state.passwordErrorText}</HelpBlock>
          </Col>
        </FormGroup>

        <FormGroup>
          <Col>
            <Checkbox>Remember me</Checkbox>
            <span
              className='cursor-pointer'
              onClick={this.forgot.bind(this)}>
              Forgot your password?
            </span>
          </Col>
        </FormGroup>

        <FormGroup>
          <Col>
            <Button bsStyle='primary'
              onClick={() => {
                this.isDisabled(['name', 'password'])
                this.props.logIn(this.state.name, this.state.password)
              }
              }>
                Sign in
              </Button>
          </Col>
        </FormGroup>
      </div>
    )
  }
}

const mapStateToProps = (state) => ({})

export default connect(mapStateToProps)(LoginPanelContent)
