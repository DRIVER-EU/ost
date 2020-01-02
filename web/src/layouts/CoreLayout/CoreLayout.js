import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'
import Auth from 'components/Auth'
import { toastr } from 'react-redux-toastr'
import { Detector } from 'react-detect-offline'
import * as actionLayout from './layout-action'

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
      version: ''
    }
  }

  static propTypes = {
    children: PropTypes.element.isRequired,
    isLoggedIn: PropTypes.any,
    user: PropTypes.object,
    borderInfo: PropTypes.any
  }

  componentWillMount () {
    this.getTrialsInfo(this.props)
    this.getBorderInfo(this.props)
    fetch('/version.txt')
    .then(response => response.text())
      .then(data => this.setState({ version: data }))
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

  render () {
    return (
      <div className='core-container'>
        <div />
        <div className='view-container'>
          <div style={styles.menubox}>
            <Auth isLoggedIn={this.props.isLoggedIn} />
            <Menu role={this.state.role} className='menu-layout' />
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
            {this.props.children}
            <div className={'version'}>
              <div className='trial-info__wrapper'>
                <p className='trial-info__text'>{this.props.borderInfo}</p>
              </div>
            </div>
          </div>
        </div>
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
