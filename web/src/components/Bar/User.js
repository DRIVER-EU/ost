import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { IndexLink, browserHistory } from 'react-router'
import { logIn, logOut } from '../../routes/Login/modules/login'
import { ListItem, DropDownMenu, MenuItem } from 'material-ui'
import './User.scss'

class UserComponent extends Component {
  constructor (props) {
    super()
  }

  static propTypes = {
    logOut: PropTypes.func,
    user: PropTypes.any,
    isLoggedIn: PropTypes.bool
  }

  componentWillReceiveProps (nextProps) {

  }

  changeActionMenu (event, key, value) {
    if (value === 2) {
      browserHistory.push('/')
    } else if (value === 3) {
      this.props.logOut()
    }
  }

  render () {
    if (this.props.isLoggedIn) {
      return (
        <DropDownMenu value={1} onChange={this.changeActionMenu.bind(this)}
          iconStyle={{ top: '0px' }}
          underlineStyle={{ borderTop: 'none' }}
          labelStyle={{ lineHeight: '48px', color: '#00497E' }}>
          <MenuItem value={1} primaryText={this.props.user.login} style={{ display: 'none' }} />
          <MenuItem value={2} primaryText='Profile' />
          <MenuItem value={3} primaryText='Log out' />
        </DropDownMenu>
      )
    } else {
      return (
        <ListItem
          primaryText='Sign in'
          style={{ color: '#00497E' }}
          className='sign-in'
          containerElement={<IndexLink to={'/login'} activeClassName='route--active' />} />
      )
    }
  }
}

const mapDispatchToProps = {
  logIn,
  logOut
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn,
  user: state.login.user
})

export default connect(mapStateToProps, mapDispatchToProps)(UserComponent)
