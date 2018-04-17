import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'
import Auth from 'components/Auth'

const styles = {
  toastStyle: {
    backgroundColor: 'green',
    textAlign: 'center'
  },
  logo: {
    position: 'absolute',
    background: '#00022A'
  },
  menubox: {
    borderBottom: '1px solid #ffc300'
  }
}

class CoreLayout extends Component {
  constructor (props) {
    super()
    this.state = {
      openToast: false,
      toastDescription: '',
      user: 'Gawron',
      role: 'Admin'
    }
  }

  static propTypes = {
    children: PropTypes.element.isRequired,
    isLoggedIn: PropTypes.any
  }

  componentWillMount () {
    localStorage.removeItem('close-pathname')
    localStorage.removeItem('cancel-pathname')
    localStorage.removeItem('close-telescope-pathname')
    localStorage.removeItem('cancel-telescope-pathname')
  }

  handleRequestClose () {
    this.setState({ openToast: false, toastDescription: '' })
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
            <div style={styles.menubox} />
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
