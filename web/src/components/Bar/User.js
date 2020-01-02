import React, { PropTypes, Component } from 'react'
import { connect } from 'react-redux'
import { logIn, logOut } from '../../routes/Login/modules/login'
import './User.scss'

class UserComponent extends Component {
  constructor (props) {
    super()
  }

  static propTypes = {
    user: PropTypes.any
  }

  componentDidMount () {
    let logOutBtn = document.getElementById('logOut')
    let self = this
    logOutBtn.onclick = function () {
      self.props.logOut()
    }
  }

  render () {
    return (
      <div className='btns__wrapper'>
        <button className='menu__btn'>{this.props.user.login}</button>
        <button className='menu__btn' id='logOut'>Log out</button>
      </div>
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
