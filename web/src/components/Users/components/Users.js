import React, { Component } from 'react'
import { RaisedButton, Checkbox, TextField, FlatButton } from 'material-ui'
import Spinner from 'react-spinkit'
import PropTypes from 'prop-types'
import 'react-table/react-table.css'
import './Users.scss'
import ReactTable from 'react-table'
import Dialog from 'material-ui/Dialog'
import _ from 'lodash'

// eslint-disable-next-line no-useless-escape
const regex = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/
class Users extends Component {
  state = {
    selectedRow: null,
    usersOptions: [],
    isUserDetailsOpen: false,
    editionMode: '',
    isDoubleClicked: false,
    user: {
      login: '',
      loginError: false,
      password: '',
      passwordError: false,
      passwordConfirmation: '',
      passwordConfirmationError: false,
      firstName: '',
      firstNameError: false,
      lastName: '',
      lastNameError: false,
      email: '',
      emailError: false,
      contact: '',
      contactError: false,
      activated: false,
      rolesIds: null,
      positionId: null
    }
  }

  static propTypes = {
    getAllUsersList: PropTypes.func,
    putUser: PropTypes.func,
    addUser: PropTypes.func,
    getSelectedUser: PropTypes.func,
    allUsersList: PropTypes.object,
    selectedUser: PropTypes.object,
    allUsersListLoading: PropTypes.bool,
    isUserLoading: PropTypes.bool
  }

  componentDidMount () {
    this.props.getAllUsersList()
  }

  componentDidUpdate (prevProps) {
    if (!_.isEqual(prevProps.allUsersList, this.props.allUsersList)) {
      if (this.props.allUsersList.data && this.props.allUsersList.data.length) {
        this.handleUsers(this.props.allUsersList.data)
      }
    }
    if (this.state.isDoubleClicked) {
      this.handleEditUser('editUser')
    }
  }

  handleUsers = (users) => {
    let usersOptions = []
    users.map(user => {
      usersOptions.push({
        id: user.id,
        login: user.login,
        firstName: user.firstName,
        lastName: user.lastName,
        deleted: false
      })
    })
    this.setState({
      usersOptions,
      isUserDetailsOpen: false,
      selectedRow: null
    })
  }

  handleAddUser = (editionMode) => {
    const user = { ...this.state.user }
    user.login = ''
    user.password = ''
    user.passwordConfirmation = ''
    user.firstName = ''
    user.lastName = ''
    user.email = ''
    user.contact = ''
    this.setState({
      user: user,
      isUserDetailsOpen: true,
      editionMode: editionMode
    })
  }

  handleEditUser = (editionMode) => {
    const user = { ...this.state.user }
    const { selectedUser } = this.props
    user.login = selectedUser.login ? selectedUser.login : ''
    user.firstName = selectedUser.firstName ? selectedUser.firstName : ''
    user.lastName = selectedUser.lastName ? selectedUser.lastName : ''
    user.email = selectedUser.email ? selectedUser.email : ''
    user.contact = selectedUser.contact ? selectedUser.contact : ''
    user.activated = selectedUser.activated ? selectedUser.activated : false
    user.rolesIds = selectedUser.roles && selectedUser.roles[0] ? [selectedUser.roles[0].id] : null
    user.positionId = selectedUser.position && selectedUser.position.id ? selectedUser.position.id : null
    this.setState({
      user: user,
      editionMode: editionMode,
      isUserDetailsOpen: true,
      isDoubleClicked: false
    })
  }

  handleOnChangeInput = (value, stateName) => {
    let updatedUser = { ...this.state.user }
    updatedUser[stateName] = value
    this.setState({ user: updatedUser })
    this.validateBlur(updatedUser, stateName)
  }

  validateBlur = (updatedUser, stateName, validationType) => {
    let updatedError = { ...updatedUser }
    let error = false
    if (stateName === 'email') {
      error = this.validateEmail(updatedError.email)
    } else if (stateName === 'password') {
      error = updatedError[stateName].length < 6
    } else if (stateName === 'passwordConfirmation') {
      error = updatedError[stateName] !== updatedError.password
    } else {
      const containsSpecialSigns = regex.test(updatedError[stateName])
      error = updatedError[stateName].length < 1 || containsSpecialSigns
    }
    if (validationType === 'formValidation') {
      return error
    } else {
      updatedError[stateName + 'Error'] = error
      this.setState({ user: updatedError })
    }
  }

  validateEmail = (email) => {
    if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
      return false
    } return true
  }

  isFormValid = () => {
    let updatedUser = { ...this.state.user }
    return (
      (this.state.editionMode === 'editUser' ? true : !this.validateBlur(updatedUser, 'login', 'formValidation')) &&
      !this.validateBlur(updatedUser, 'firstName', 'formValidation') &&
      !this.validateBlur(updatedUser, 'lastName', 'formValidation') &&
      !this.validateBlur(updatedUser, 'email', 'formValidation') &&
      !this.validateBlur(updatedUser, 'contact', 'formValidation') &&
      (this.state.editionMode === 'editUser' ? true : !this.validateBlur(updatedUser, 'password', 'formValidation')) &&
      // eslint-disable-next-line max-len
      (this.state.editionMode === 'editUser' ? true : !this.validateBlur(updatedUser, 'passwordConfirmation', 'formValidation'))
    )
  }

  handleSelectedUser = (selectedUser, mode) => {
    this.props.getSelectedUser(selectedUser.id)
    if (mode === 'doubleClick') {
      this.setState({
        isUserDetailsOpen: true
      })
    }
  }

  saveUser = () => {
    const { user } = this.state
    if (this.state.editionMode === 'editUser') {
      const userId = this.state.selectedRow && this.state.selectedRow.id ? this.state.selectedRow.id : null
      const updatedUser = {
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        contact: user.contact,
        activated: user.activated,
        rolesIds: user.rolesIds,
        positionId: user.positionId
      }
      this.props.putUser(updatedUser, userId)
    } else {
      const updatedUser = {
        login: user.login,
        password: user.password,
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        contact: user.contact,
        activated: user.activated,
        rolesIds: [1],
        positionId: 2
      }
      this.props.addUser(updatedUser)
    }
  }

  handleCloseEditUser = () => {
    this.setState({
      isUserDetailsOpen: false,
      selectedRow: null
    })
  }

  isSelectedUser = () => {
    if (this.state.selectedRow && this.state.selectedRow.id) {
      return this.state.selectedRow.id === this.props.selectedUser.id
    } else {
      return false
    }
  }

  render () {
    const columns = [
      {
        Header: 'User List',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 90
          },
          {
            Header: 'Login',
            accessor: 'login'
          },
          {
            Header: 'FirstName',
            accessor: 'firstName'
          },
          {
            Header: 'LastName',
            accessor: 'lastName'
          },
          {
            Header: 'Deleted',
            accessor: 'deleted'
          }
        ]
      }
    ]
    const {
      isUserDetailsOpen,
      selectedRow,
      usersOptions,
      user
    } = this.state
    const {
      login,
      firstName,
      lastName,
      email,
      contact,
      password,
      passwordConfirmation
    } = user
    const isFormValid = this.isFormValid()
    const isSelectedUser = !this.isSelectedUser()
    return (
      <div className='users-container users-wrapper'>
        <div style={{ width: '100%', height: '100%' }}>
          {this.props.allUsersListLoading
          ? <div className='spinner-box'>
            <div className={'spinner'}>
              <Spinner
                style={{ margin: '200px auto', width: '50px' }}
                fadeIn='none'
                className={'spin-item'}
                color={'#fdb913'}
                name='ball-spin-fade-loader' />
            </div>
          </div>
          : <ReactTable
            data={usersOptions}
            columns={columns}
            multiSort
            showPagination
            minRows={0}
            getTdProps={(state, rowInfo) => {
              if (rowInfo && rowInfo.row) {
                return {
                  onClick: e => {
                    this.handleSelectedUser(rowInfo.original)
                    this.setState({
                      selectedRow: rowInfo.original,
                      isDoubleClicked: false
                    })
                  },
                  onDoubleClick: e => {
                    this.handleSelectedUser(rowInfo.original)
                    this.setState({
                      selectedRow: rowInfo.original,
                      isDoubleClicked: true
                    })
                  },
                  style: {
                    background: this.state.selectedRow
                      ? rowInfo.original.id ===
                        this.state.selectedRow.id
                        ? '#e5e5e5'
                        : ''
                      : '',
                    cursor: 'pointer'
                  }
                }
              }
            }} />}
        </div>
        <div className='users-btn-container'>
          <a href='admin-home'>
            <RaisedButton
              buttonStyle={{ width: '200px' }}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              label='Back'
              type='button' />
          </a>
          <RaisedButton
            onClick={() => this.handleAddUser('newUser')}
            buttonStyle={{ width: '200px', marginLeft: '25px' }}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            label='+ New'
            type='button' />
          <RaisedButton
            disabled={isSelectedUser}
            onClick={() => this.handleEditUser('editUser')}
            buttonStyle={{ width: '200px', marginLeft: '25px' }}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            label='Edit'
            type='button' />
        </div>
        {this.props.isUserLoading
        ? <div className='spinner-box'>
          <div className={'spinner'}>
            <Spinner
              style={{ margin: '200px auto', width: '50px' }}
              fadeIn='none'
              className={'spin-item'}
              color={'#fdb913'}
              name='ball-spin-fade-loader' />
          </div>
        </div>
        : <Dialog open={isUserDetailsOpen}>
          <h2>User details</h2>
          <div className='user-info-save'>
            <p>{selectedRow && <span>ID: {selectedRow.id}</span>}</p>
            <p>{selectedRow && selectedRow.deleted ? <Checkbox checked disabled />
            : <Checkbox disabled />} <p>Deleted</p></p>
            <RaisedButton
              onClick={this.saveUser}
              disabled={!isFormValid}
              buttonStyle={{ width: '200px' }}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              label='Save'
              type='button' />
          </div>
          <div>
            {this.state.editionMode !== 'editUser' && <TextField
              value={login}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'login')}
              errorText={user.loginError ? 'This field is required' : ''}
              fullWidth
              type='text'
              floatingLabelFixed
              floatingLabelText='Login' />}
            {this.state.editionMode !== 'editUser' && <TextField
              value={password}
              autoComplete='new-password'
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'password')}
              errorText={user.passwordError ? 'This field is required. Min 6 characters.' : ''}
              floatingLabelText='Password'
              fullWidth
              floatingLabelFixed
              type='password'
              style={{ marginRight: '20px' }} />}
            {this.state.editionMode !== 'editUser' && <TextField
              value={passwordConfirmation}
              autoComplete='new-password'
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'passwordConfirmation')}
              errorText={user.passwordConfirmationError ? 'Required field. Both passwords must be the same.' : ''}
              floatingLabelText='Confirm Password'
              fullWidth
              floatingLabelFixed
              type='password' />}
            <TextField
              value={firstName}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'firstName')}
              errorText={user.firstNameError ? 'This field is required' : ''}
              fullWidth
              floatingLabelFixed
              floatingLabelText='First Name' />
            <TextField
              value={lastName}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'lastName')}
              errorText={user.lastNameError ? 'This field is required' : ''}
              fullWidth
              floatingLabelFixed
              floatingLabelText='Last Name' />
            <TextField
              value={email}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'email')}
              errorText={user.emailError ? 'This field is required' : ''}
              fullWidth
              required
              type='email'
              floatingLabelFixed
              floatingLabelText='email' />
            <TextField
              value={contact}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'contact')}
              errorText={user.contactError ? 'This field is required' : ''}
              fullWidth
              floatingLabelFixed
              floatingLabelText='Contact(Phone)' />
            <FlatButton
              label='Cancel'
              secondary
              onClick={this.handleCloseEditUser} />
          </div>
        </Dialog>}
      </div>
    )
  }
}

export default Users
