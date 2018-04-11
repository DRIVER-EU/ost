import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { IndexLink } from 'react-router'
import { logIn, logOut } from '../../routes/Login/modules/login'
import { ListItem } from 'material-ui/List'
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

  render () {
    if (this.props.isLoggedIn) {
      return (
        <ListItem
          className='user-name'
          primaryText={`${this.props.user.login}`}
          primaryTogglesNestedList
          nestedListStyle={{ position: 'absolute', background: '#fff', borderBottom: '1px solid #fdb913' }}
          nestedItems={[
            <ListItem
              primaryText='Profile'
              style={{ color: '#00497E' }}
              containerElement={<IndexLink to='/profile' activeClassName='route--active' />} />,
            <ListItem
              primaryText='Log out'
              style={{ color: '#00497E' }}
              onClick={() => { this.props.logOut() }}
              containerElement={<IndexLink activeClassName='route--active' />} />
          ]}
        />
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
