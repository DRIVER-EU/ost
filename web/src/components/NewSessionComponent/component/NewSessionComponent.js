import React, { Component } from 'react'
import { RaisedButton, FloatingActionButton } from 'material-ui'
import FontIcon from 'material-ui/FontIcon'
import SelectField from 'material-ui/SelectField'
import Checkbox from 'material-ui/Checkbox'
import TextField from 'material-ui/TextField'
import './NewSessionComponent.scss'
import ContentAdd from 'material-ui/svg-icons/content/add'
import PropTypes from 'prop-types'
import MenuItem from 'material-ui/MenuItem'
import { browserHistory } from 'react-router'
import ReactTooltip from 'react-tooltip'
import _ from 'lodash'

const statusList = [
  { id: 0, name: 'ACTIVE' },
  { id: 1, name: 'SUSPENDED' }
]

class NewSessionComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      rolesList: [],
      stagesList: [],
      stageItem: '',
      userItems: []
    }
  }

  static propTypes = {
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object,
    params: PropTypes.any
  }

  componentWillMount () {
    this.props.getRoles(this.props.params.id)
    this.props.getStages(this.props.params.id)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.stagesList &&
      nextProps.stagesList !== this.props.stagesList) {
      this.setState({ stagesList: nextProps.stagesList.data })
    }
    if (nextProps.rolesList &&
      nextProps.rolesList !== this.props.rolesList) {
      this.setState({ rolesList: nextProps.rolesList.data },
        () => this.createUserItem())
    }
  }

  handleChangeDropDown (stateName, event, index, value) {
    let change = { ...this.state }
    change[stateName] = value
    this.setState(change)
  }

  createUserItem () {
    let items = [ ...this.state.userItems ]
    let lastItem = _.last(items)
    let id = 0
    if (lastItem) {
      id = lastItem.id + 1
    }
    items.push({
      id: id,
      email: '',
      role: []
    })
    this.setState({
      userItems: items
    })
  }

  handleChangeCheckbox (id, roleId) {
    let change = [ ...this.state.userItems ]
    let checkIndex = _.findIndex(change, { id: id })
    let isIn = _.findIndex(change[checkIndex].role, { id: roleId })
    if (isIn !== -1) {
      change[checkIndex].role.splice(isIn, 1)
    } else {
      change[checkIndex].role.push({ id: roleId })
    }
    this.setState({ userItems: change })
  }

  handleCheckRole (object, roleId) {
    let isCheck = false
    if (object.role.length !== 0) {
      object.role.map(element => {
        if (element.id === roleId) {
          isCheck = true
        }
      })
      return isCheck
    }
  }

  handleRemoveUser (id) {
    let change = [ ...this.state.userItems ]
    let checkIndex = _.findIndex(change, { id: id })
    change.splice(checkIndex, 1)
    this.setState({ userItems: change })
  }

  back = () => {
    browserHistory.push(`/trial-manager`)
  }

  handleChangeEmail (object, e) {
    let change = [ ...this.state.userItems ]
    let checkIndex = _.findIndex(change, { id: object.id })
    change[checkIndex].email = e.target.value
    this.setState({ userItems: change })
  }

  handleValidEmail (object) {
    let regex = new RegExp('[^@]+@[^@]+\\.[^@]+')
    let validated = regex.test(object.email)
    if (!validated) {
      return 'The email is not correct!'
    } else {
      return ''
    }
  }

  validateForm () {
    let isValid = true
    let validTab = []
    if (this.state.stageItem !== '' && this.state.status !== '' && this.state.userItems.length !== 0) {
      this.state.userItems.map(object => {
        if (object.email === '' || object.role.length === 0) {
          validTab.push(object.email)
        }
      })
      if (validTab.length === 0) {
        isValid = false
      }
    }
    return isValid
  }

  send () {
    // POST method
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ height: '100%' }}>
          <div className='new-session-container'>
            <h2 style={{ display: 'inline-block' }}>New Session</h2>
            <div className={'buttons-obs'} style={{ float: 'right', display: 'inline-block' }}>
              <RaisedButton
                buttonStyle={{ width: 300 }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Back to select the Trial'
                secondary
                icon={<FontIcon className='material-icons' style={{ margin: 0 }}>
                  <i className='material-icons'>keyboard_arrow_left</i></FontIcon>}
                onClick={this.back.bind(this)} />
            </div>
            <div style={{
              display: 'flex',
              flexDirection: 'row',
              flexFlow: 'row wrap',
              justifyContent: 'space-between',
              alignItems: 'center'
            }}>
              <div className='element'>
                <h3>Initial Stage:</h3>
                <SelectField
                  value={this.state.stageItem}
                  floatingLabelText='Stage'
                  onChange={this.handleChangeDropDown.bind(this, 'stageItem')} >
                  {this.state.stagesList.length !== 0 && this.state.stagesList.map((index) => (
                    <MenuItem
                      key={index.id}
                      value={index.id}
                      style={{ color: 'grey' }}
                      primaryText={index.name} />
                ))}
                </SelectField>
              </div>
              <div className='element'>
                <h3>Status:</h3>
                <SelectField
                  value={this.state.status}
                  floatingLabelText='Change Session Status'
                  onChange={this.handleChangeDropDown.bind(this, 'status')} >
                  {statusList.map((index) => (
                    <MenuItem
                      key={index.id}
                      value={index.name}
                      style={{ color: 'grey' }}
                      primaryText={index.name} />
                ))}
                </SelectField>
              </div>
            </div>
            <h3 style={{ marginTop: 50, marginBottom: 28 }}>Users:</h3>
            {this.state.userItems.length !== 0 && this.state.userItems.map((object, index) => {
              return (
                <div key={index} className='users-row'>
                  <TextField
                    style={{ minWidth: 200, maxWidth: 200 }}
                    value={object.email}
                    hintText='enter the email'
                    errorText={object.email !== '' && this.handleValidEmail(object)}
                    fullWidth
                    onChange={this.handleChangeEmail.bind(this, object)} />
                  <div style={{
                    display: 'flex',
                    flexDirection: 'row',
                    overflowX: 'scroll',
                    overflowY: 'hidden',
                    width: '100%',
                    marginLeft: 15
                  }}>
                    {this.state.rolesList.length !== 0 && this.state.rolesList.map((role, index) => {
                      return (
                        <Checkbox
                          data-tip={role.name}
                          key={index}
                          label={role.name}
                          defaultChecked={this.handleCheckRole(object, role.id)}
                          labelStyle={{
                            minWidth: 100,
                            maxWidth: 200,
                            height: 30,
                            overflow: 'hidden',
                            textOverflow: 'ellipsis',
                            whiteSpace: 'nowrap'
                          }}
                          onCheck={this.handleChangeCheckbox.bind(this, object.id, role.id)}
                    />)
                    }
                    )}
                    <ReactTooltip place='top' type='dark' effect='solid' />
                  </div>
                  <i className='material-icons' onClick={() => this.handleRemoveUser(object.id)}>
                    clear
                  </i>
                </div>
              )
            })}
            <div style={{ float: 'right' }}>
              <FloatingActionButton
                onClick={this.createUserItem.bind(this)}
                secondary>
                <ContentAdd />
              </FloatingActionButton>
            </div>
            <RaisedButton
              label='Submit'
              onClick={this.send.bind(this)}
              style={{ display: 'table', margin: '0 auto', width: 200, marginTop: 120 }}
              disabled={this.validateForm()}
              primary />
          </div>
        </div>
      </div>
    )
  }
}

export default NewSessionComponent
