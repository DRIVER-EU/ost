import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'
import Auth from 'components/Auth'

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

  componentWillReceiveProps (nextProps) {
    if (nextProps.user && nextProps.user !== this.props.user &&
      nextProps.user.roles[0] !== this.state.role) {
      this.setState({ role: nextProps.user.roles[0] })
    }
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
