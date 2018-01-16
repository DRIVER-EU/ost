import React from 'react'
import './Bar.scss'
import PropTypes from 'prop-types'

export const Bar = (props) => (
  <div />
)
Bar.propTypes = {
  logOut: PropTypes.func,
  redirect: PropTypes.func,
  user: PropTypes.string,
  isLoggedIn: PropTypes.bool,
  add: PropTypes.string,
  admin: PropTypes.string
}

export default Bar
