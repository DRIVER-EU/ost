import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'
import Auth from 'components/Auth'
import { toastr } from 'react-redux-toastr'
import { Detector } from 'react-detect-offline'

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
      role: ''
    }
  }

  static propTypes = {
    children: PropTypes.element.isRequired,
    isLoggedIn: PropTypes.any,
    user: PropTypes.object
  }

  // componentDidMount () {
  //   this.interval = setInterval(() => {
  //     window.navigator.onLine && (this.checkMod = true)
  //     if (!window.navigator.onLine && this.checkMod) {
  //       this.updateIndicator()
  //       this.checkMod = false
  //     }
  //   }, 1000)
  // }

  componentWillReceiveProps (nextProps) {
    if (nextProps.user && nextProps.user !== this.props.user &&
      nextProps.user.roles[0] !== this.state.role) {
      this.setState({ role: nextProps.user.roles[0] })
    }
  }

  // componentWillUnmount () {
  //   clearInterval(this.interval)
  // }

  updateIndicator () {
    toastr.error('Observation form', 'Internet not available, please use paper forms', toastrOptions)
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
                    : toastr.error('Observation form',
                      'Internet not available, please use paper forms', toastrOptions) }
                </div>
              )}
            />
            {this.props.children}
          </div>
        </div>
      </div>
    )
  }
}

const mapDispatchToProps = {
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn,
  user: state.login.user
})

export default connect(mapStateToProps, mapDispatchToProps)(CoreLayout)
