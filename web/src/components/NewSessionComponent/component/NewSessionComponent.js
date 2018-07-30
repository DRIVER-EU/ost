import React, { Component } from 'react'
import { RaisedButton, FloatingActionButton } from 'material-ui'
import FontIcon from 'material-ui/FontIcon'
import SelectField from 'material-ui/SelectField'
import Checkbox from 'material-ui/Checkbox'
import './NewSessionComponent.scss'
import ContentAdd from 'material-ui/svg-icons/content/add'
import DateTimePicker from 'material-ui-datetimepicker'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import moment from 'moment'
import PropTypes from 'prop-types'
import MenuItem from 'material-ui/MenuItem'
import { browserHistory } from 'react-router'
import ReactTooltip from 'react-tooltip'
import _ from 'lodash'

class NewSessionComponent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      dateTime: moment(new Date().getTime()).format('DD-MM-YYYY hh:mm:ss'),
      usersList: [],
      rolesList: [],
      stagesList: [],
      stageItem: '',
      userItems: []
    }
  }

  static propTypes = {
    getUsers: PropTypes.func,
    usersList: PropTypes.object,
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object,
    params: PropTypes.any
  }

  componentWillMount () {
    this.props.getUsers(this.props.params.id)
    this.props.getRoles(this.props.params.id)
    this.props.getStages(this.props.params.id)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.stagesList &&
      nextProps.stagesList !== this.props.stagesList) {
      this.setState({ stagesList: nextProps.stagesList.data })
    }
    if (nextProps.usersList &&
      nextProps.usersList !== this.props.usersList) {
      this.setState({ usersList: nextProps.usersList.data })
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
      userId: '',
      role: []
    })
    this.setState({
      userItems: items
    })
  }

  handleChangeDropDownList (id, event, index, value) {
    let change = [ ...this.state.userItems ]
    let checkIndex = _.findIndex(change, { id: id })
    if (checkIndex !== -1) {
      change[checkIndex].userId = value
    } else {
      change.push({
        id: id,
        userId: value,
        role: []
      })
    }
    this.setState({ userItems: change })
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

  setDate = (dateTime) => this.setState({ dateTime: moment(dateTime).format('DD-MM-YYYY hh:mm:ss') })

  validateForm () {
    let isValid = true
    let validTab = []
    if (this.state.stageItem !== '' && this.state.dateTime !== '' && this.state.userItems.length !== 0) {
      this.state.userItems.map(object => {
        if (object.userId === '' || object.role.length === 0) {
          validTab.push(object.userId)
        }
      })
      if (validTab.length === 0) {
        isValid = false
      }
    }
    return isValid
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
                <h3>Time:</h3>
                <DateTimePicker
                  onChange={this.setDate}
                  DatePicker={DatePickerDialog}
                  TimePicker={TimePickerDialog}
                  value={this.state.dateTime}
                  textFieldStyle={{ width:200 }}
                  format='YYYY-MM-DD kk:mm' />
              </div>
              <div className='element'>
                <h3>Start Stage:</h3>
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
            </div>
            <h3 style={{ marginTop: 50, marginBottom: 28 }}>Users:</h3>
            {this.state.userItems.length !== 0 && this.state.userItems.map((object, index) => {
              return (
                <div key={index} className='users-row'>
                  <SelectField
                    style={{ minWidth: 200, maxWidth: 200 }}
                    value={object.userId}
                    floatingLabelText='Select User'
                    onChange={this.handleChangeDropDownList.bind(this, object.id)} >
                    {this.state.usersList && this.state.usersList.map((index) => (
                      <MenuItem
                        key={index.id}
                        value={index.id}
                        style={{ color: 'grey' }}
                        primaryText={`${index.firstName} ${index.lastName}`} />
                ))}
                  </SelectField>
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
              onClick={this.back.bind(this)}
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
