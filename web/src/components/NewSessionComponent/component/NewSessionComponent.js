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
      isStateValid: false,
      isUserValid: false,
      userItemsArray: [],
      indexUserItem: 0
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
      this.setState({ rolesList: nextProps.rolesList.data })
    }
  }

  handleChange = (event, index, value) => this.setState({ value })

  handleChangeDropDown (stateName, event, index, value) {
    let change = {}
    change[stateName] = value
    this.setState(change)
    if (stateName === 'stageItem') {
      this.setState({ isStateValid: true })
    }
    if (stateName === 'userItem') {
      this.setState({ isUserValid: true })
    }
  }

  createUserItem () {
    let item = [ ...this.state.userItemsArray ]
    item.push({
      id: this.state.indexUserItem,
      userId: '',
      role: []
    })
    this.setState({
      userItemsArray: item,
      indexUserItem: this.state.indexUserItem + 1
    })
  }

  handleChangeDropDownList (id, event, index, value) {
    let change = [ ...this.state.userItemsArray ]
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
    this.setState({ userItemsArray: change })
  }

  handleChangeCheckbox (index, indexButton) {
    let change = [ ...this.state.userItemsArray ]
    let checkIndex = _.findIndex(change, { id: index })
    let isIn = _.findIndex(change[checkIndex].role, { id: indexButton })
    if (isIn !== -1) {
      change[checkIndex].role.splice(isIn, 1)
    } else {
      change[checkIndex].role.push({ id: indexButton })
    }
    this.setState({ userItemsArray: change })
  }

  back = () => {
    browserHistory.push(`/trial-manager`)
  }

  setDate = (dateTime) => this.setState({ dateTime: moment(dateTime).format('DD-MM-YYYY hh:mm:ss') })

  validateForm () {
    let isTrue = this.state.isStateValid && this.state.isUserValid
    return isTrue
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
              alignItems: 'center',
              marginTop: 40
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
                  {this.state.stagesList !== undefined && this.state.stagesList.map((index) => (
                    <MenuItem
                      key={index.id}
                      value={index.id}
                      style={{ color: 'grey' }}
                      primaryText={index.name} />
                ))}
                </SelectField>
              </div>
            </div>
            <h3 style={{ paddingTop: 180, marginBottom: 22 }}>Users:</h3>
            {this.state.userItemsArray.map((object, index) => {
              return (
                <div key={index} style={{
                  display: 'flex',
                  alignItems: 'flex-end',
                  height: 40,
                  marginBottom: 15 }}>
                  <SelectField
                    style={{ minWidth: 200, maxWidth: 200 }}
                    value={object.userId}
                    floatingLabelText='Select User'
                    onChange={this.handleChangeDropDownList.bind(this, index)} >
                    {this.state.usersList !== undefined && this.state.usersList.map((index) => (
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
                    width: '100%',
                    marginLeft: 15
                  }}>
                    {this.state.rolesList.map((user, indexChecked) => (
                      <Checkbox
                        key={indexChecked}
                        label={user.name}
                        labelStyle={{
                          minWidth: 100,
                          maxWidth: 200,
                          height: 30,
                          overflow: 'hidden',
                          textOverflow: 'ellipsis',
                          whiteSpace: 'nowrap'
                        }}
                        onCheck={this.handleChangeCheckbox.bind(this, index, indexChecked)}
                    />
                    ))}
                  </div>
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
              disabled={!this.validateForm()}
              primary />
          </div>
        </div>
      </div>
    )
  }
}

export default NewSessionComponent
