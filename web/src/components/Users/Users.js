import React, { Component } from 'react'
import { RaisedButton, Checkbox, TextField, FlatButton } from 'material-ui'
import ReactTable from 'react-table'
import 'react-table/react-table.css'
import './Users.scss'
import Dialog from 'material-ui/Dialog'
import axios from 'axios'
import { origin } from './../../config/Api'
import { getHeaders } from '../../store/addons'

class Users extends Component {
  state = {
    selectedRow: null,
    data: [
      { id: 1, login: 'a', firstName: 'k', lastName: 's', deleted: 'yes' },
      { id: 2, login: 'b', firstName: 'l', lastName: 't', deleted: 'no' },
      { id: 3, login: 'c', firstName: 'm', lastName: 'u', deleted: 'yes' },
      { id: 4, login: 'd', firstName: 'n', lastName: 'x', deleted: 'no' },
      { id: 5, login: 'e', firstName: 'o', lastName: 'y', deleted: 'yes' },
      { id: 6, login: 'f', firstName: 'p', lastName: 'z', deleted: 'no' },
      { id: 7, login: 'g', firstName: 'q', lastName: 'zz', deleted: 'yes' }
    ],
    isUserDetailsOpen: false,
    user: {
      login: '',
      password: '',
      firstName: '',
      lastName: '',
      email: '',
      contact: ''
    }
  }

  componentDidMount () {
    // const url = 'http://{{IP-Address}}/api/auth/users?page=0&size=10&sort=login,asc&sort=lastName,desc'
    axios.get(`${origin}/api/auth/users?page=0&size=10&sort=login,asc&sort=lastName,desc`, getHeaders())
    .then(res => {
      console.log('DATA ::: ', res.data)
    })
    .catch(err => {
      console.log('ERROR :: ', err)
    })
  }

  handleNewUser = () => {
    console.log('new user')
  }

  handleEditUser = () => {
    this.setState({
      isUserDetailsOpen: true
    })
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
      contact
    } = this.state
    return (
      <div className='users-container users-wrapper'>
        <div>
          <ReactTable
            data={this.state.data}
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
            }} />
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
            onClick={this.handleNewUser}
            buttonStyle={{ width: '200px', marginLeft: '25px' }}
            backgroundColor='#244C7B'
            labelColor='#FCB636'
            label='+ New'
            type='button' />
          <RaisedButton
            disabled={!this.state.selectedRow}
            onClick={this.handleEditUser}
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
              fullWidth
              floatingLabelText='Login' />
            <TextField
              value={password}
              floatingLabelText='Password'
              type='password'
              style={{ marginRight: '20px' }} />
            <TextField
              value={password}
              floatingLabelText='Password'
              type='password' />
            <TextField
              value={firstName}
              fullWidth
              floatingLabelText='First Name' />
            <TextField
              value={lastName}
              fullWidth
              floatingLabelText='Last Name' />
            <TextField
              value={email}
              fullWidth
              type='email'
              floatingLabelText='email' />
            <TextField
              value={contact}
              fullWidth
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
