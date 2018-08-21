import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { logIn, logOut } from '../../routes/Login/modules/login'
import { DropDownMenu, MenuItem } from 'material-ui'
import './User.scss'

class UserComponent extends Component {
  constructor (props) {
    super()
  }

  static propTypes = {
    logOut: PropTypes.func,
    user: PropTypes.any
  }

  changeActionMenu (event, key, value) {
    if (value === 2) {
      this.props.logOut()
    }
  }

  render () {
    return (
      <DropDownMenu value={1} onChange={this.changeActionMenu.bind(this)}
        iconStyle={{ top: '0px' }}
        underlineStyle={{ borderTop: 'none' }}
        labelStyle={{ lineHeight: '48px', color: '#00497E' }}>
        <MenuItem value={1} primaryText={this.props.user.login} style={{ display: 'none' }} />
        <MenuItem value={2} primaryText='Log out' />
      </DropDownMenu>
    )
  }
}

const mapDispatchToProps = {
  logIn,
  logOut
}

const mapStateToProps = (state) => ({
  user: state.login.user
})

export default connect(mapStateToProps, mapDispatchToProps)(UserComponent)
