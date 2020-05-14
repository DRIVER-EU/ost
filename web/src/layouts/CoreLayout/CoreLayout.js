import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'
// import Auth from 'components/Auth'
import { toastr } from 'react-redux-toastr'
import { Detector } from 'react-detect-offline'
import * as actionLayout from './layout-action'
import Keycloak from 'keycloak-js'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import keycloakJson from '../../../public/keycloak.json'

const toastrOptions = {
  timeOut: 3000
}

const styles = {
  menubox: {
    borderBottom: '1px solid #ffc300',
    height: 74
  }
}

class CoreLayout extends Component {
  constructor (props) {
    super()
    this.state = {
      role: '',
      version: '',
      keycloak: null,
      isReady: false
    }
  }

  static propTypes = {
    children: PropTypes.element.isRequired,
    // isLoggedIn: PropTypes.any,
    user: PropTypes.object,
    borderInfo: PropTypes.any,
    logIn: PropTypes.func,
    logOut: PropTypes.func
  }

  componentDidMount () {
    if (navigator.onLine) {
      console.log('Online')
      this.logInToKeyCloak()
    } else if (
      localStorage.getItem('driveruser') &&
      localStorage.getItem('drivertoken') &&
      localStorage.getItem('drivertoken') &&
      localStorage.getItem('driverrole')) {
      console.log('offline but logged in')
      this.redirect(localStorage.getItem('driverrole'))
      this.setState({ isReady: true })
    } else {
      console.log('offline and log out')
      window.location.pathname !== '/' && browserHistory.push('/')
      this.setState({ isReady: true })
    }
  }

  getTrialsInfo = (props) => {
    props.getTrialInfo(props.user)
  }

  getBorderInfo = (props) => {
    props.getBorderInfo(props.user)
  }
  componentWillReceiveProps (nextProps) {
    this.getTrialsInfo(nextProps)

    if (nextProps.user && nextProps.user !== this.props.user &&
      nextProps.user.roles[0] !== this.state.role) {
      this.setState({ role: nextProps.user.roles[0] })
    }
  }

  updateIndicator () {
    toastr.warning('Offline mode', 'Welcome to offline mode', toastrOptions)
  }

  logInToKeyCloak = () => {
    const keycloak = Keycloak(keycloakJson)
    const origin = window.location.origin
    if (!origin.includes('localhost')) {
      keycloakJson.url = `${origin}/oauth2`
      keycloakJson['auth-server-url'] = `${origin}/oauth2`
    }
    keycloak.init({
      onLoad: 'check-sso',
      promiseType: 'native',
      silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
      pkceMethod: 'S256' })
    keycloak.onAuthLogout = () => {
      this.setState({ isReady: false })
      this.props.logOut()
      localStorage.removeItem('driveruser')
      localStorage.removeItem('drivertoken')
      localStorage.removeItem('driverrole')
      localStorage.removeItem('openTrial')
      localStorage.removeItem('online')
      window.location.pathname !== '/' && browserHistory.push('/')
      keycloak.logout()
    }
    keycloak.onReady = () => {
      this.setState({ keycloak, isReady: true })
      if (keycloak.authenticated) {
        this.getTrialsInfo(this.props)
        this.getBorderInfo(this.props)
        fetch('/version.txt')
        .then(response => response.text())
          .then(data => this.setState({ version: data }))
      }
    }
    keycloak.onAuthSuccess = () => {
      const user = {
        login:  keycloak.tokenParsed.name,
        firstName: keycloak.tokenParsed.given_name,
        lastName: keycloak.tokenParsed.family_name,
        email: keycloak.tokenParsed.email,
        roles: keycloak.tokenParsed.realm_access.roles,
        permissions: keycloak.tokenParsed.realm_access.roles
      }
      this.props.logIn(user)
      const role = keycloak.tokenParsed.realm_access.roles.includes('ost_admin') ? 'ost_admin' : 'ost_observer'
      // if (role === 'ost_admin') {
      //   window.location.pathname === '/' && browserHistory.push('/admin-home')
      // } else if (role === 'ost_observer') {
      //   window.location.pathname === '/' && browserHistory.push('/trials')
      // }
      this.redirect(role)
      localStorage.setItem('driveruser', JSON.stringify(user))
      localStorage.setItem('drivertoken', keycloak.token)
      localStorage.setItem('driverrole', role)
      localStorage.setItem('openTrial', false)
      localStorage.setItem('online', true)
    }
    keycloak.onTokenExpired = () => {
      keycloak.updateToken().then(() => {
        localStorage.setItem('drivertoken', keycloak.token)
        console.log(123456)
      })
    }
  }

  redirect (role) {
    if (role === 'ost_admin') {
      window.location.pathname === '/' && browserHistory.push('/admin-home')
    } else if (role === 'ost_observer') {
      window.location.pathname === '/' && browserHistory.push('/trials')
    }
  }

  render () {
    return (
      <div className='core-container'>
        <div />
        {this.state.isReady
        ? <div className='view-container'>
          <div style={styles.menubox}>
            {/* <Auth isLoggedIn={this.props.isLoggedIn} /> */}
            <Menu role={this.state.role} keycloak={this.state.keycloak} className='menu-layout' />
          </div>
          <div className='core-layout__viewport'>
            <Detector
              render={({ online }) => (
                <div style={{ display: 'none' }}>
                  {online ? ''
                    : toastr.warning('Offline mode',
                      'Welcome to offline mode', toastrOptions) }
                </div>
              )}
            />
            {React.cloneElement(this.props.children, { keycloak: this.state.keycloak })}
            <div className={'version'}>
              <div className='trial-info__wrapper'>
                <p className='trial-info__text'>{this.props.borderInfo}</p>
              </div>
            </div>
          </div>
        </div>
      : <div style={{ position: 'absolute', top: '50%', right: '50%' }}>
        <Spinner fadeIn='none' className={'spin-item'} color={'#fdb913'} name='ball-spin-fade-loader' />
      </div>}
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn,
  user: state.login.user,
  trial: state.layout.trial,
  borderInfo: state.layout.borderInfo
})

export default connect(mapStateToProps, actionLayout)(CoreLayout)
