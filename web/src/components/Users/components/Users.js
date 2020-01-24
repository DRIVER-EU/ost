import React, { Component } from 'react'
import { RaisedButton, Checkbox, TextField, FlatButton } from 'material-ui'
import Spinner from 'react-spinkit'
import PropTypes from 'prop-types'
import 'react-table/react-table.css'
import './Users.scss'
import ReactTable from 'react-table'
import Dialog from 'material-ui/Dialog'
import _ from 'lodash'

class Users extends Component {
  state = {
    selectedRow: null,
    usersOptions: [],
    isUserDetailsOpen: false,
    user: {
      login: '',
      loginError: false,
      password: '',
      passwordError: false,
      passwordConformation: '',
      passwordConformationError: false,
      firstName: '',
      firstNameError: false,
      lastName: '',
      lastNameError: false,
      email: '',
      emailError: false,
      contact: '',
      contactError: false
    },
    editionStarted: false
  }

  static propTypes = {
    getAllUsersList: PropTypes.func,
    allUsersList: PropTypes.object,
    allUsersListLoading: PropTypes.bool
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
      usersOptions
    })
  }

  handleUser = (type) => {
    this.setState({
      isUserDetailsOpen: true
    })
  }

  handleOnChangeInput = (value, stateName) => {
    let updatedUser = { ...this.state.user }
    updatedUser[stateName] = value
    this.setState({ user: updatedUser, editionStarted: true })
    this.validateBlur(updatedUser, stateName)
  }

  validateBlur = (updatedUser, stateName) => {
    let updatedError = { ...updatedUser }
    const error = updatedError[stateName].length < 1
    updatedError[stateName + 'Error'] = error
    this.setState({ user: updatedError })
  }

  saveUser = () => {
    console.log('save')
  }

  handleCloseEditUser = () => {
    this.setState({
      isUserDetailsOpen: false
    })
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
      login,
      password,
      firstName,
      lastName,
      email,
      contact,
      usersOptions,
      user
    } = this.state
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
                    this.setState({
                      selectedRow: rowInfo.original
                    })
                  },
                  onDoubleClick: e => {
                    this.viewQuestion()
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
            onClick={() => this.handleUser('newUser')}
            buttonStyle={{ width: '200px', marginLeft: '25px' }}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            label='+ New'
            type='button' />
          <RaisedButton
            disabled={!this.state.selectedRow}
            onClick={() => this.handleUser('editUser')}
            buttonStyle={{ width: '200px', marginLeft: '25px' }}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            label='Edit'
            type='button' />
        </div>
        <Dialog open={isUserDetailsOpen}>
          <h2>User details</h2>
          <div className='user-info-save'>
            <p>{selectedRow && <span>ID: {selectedRow.id}</span>}</p>
            <p>{selectedRow && selectedRow.deleted ? <Checkbox checked disabled />
            : <Checkbox disabled />} <p>Deleted</p></p>
            <RaisedButton
              onClick={this.saveUser}
              buttonStyle={{ width: '200px' }}
              backgroundColor='#244C7B'
              labelColor='#FCB636'
              label='Save'
              type='button' />
          </div>
          <div>
            <TextField
              value={login}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'login')}
              errorText={user.loginError ? 'This field is required' : ''}
              fullWidth
              type='text'
              floatingLabelFixed
              floatingLabelText='Login' />
            <TextField
              value={password}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'password')}
              errorText={user.passwordError ? 'This field is required' : ''}
              floatingLabelText='Password'
              floatingLabelFixed
              type='text'
              style={{ marginRight: '20px' }} />
            <TextField
              value={password}
              onChange={(e) => this.handleOnChangeInput(e.target.value, 'passwordConformation')}
              errorText={user.passwordConformationError ? 'This field is required' : ''}
              floatingLabelText='Confirm Password'
              floatingLabelFixed
              type='text' />
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
        </Dialog>
      </div>
    )
  }
}

export default Users
