import React, { Component } from 'react'
import { RaisedButton, FloatingActionButton } from 'material-ui'
import FontIcon from 'material-ui/FontIcon'
import SelectField from 'material-ui/SelectField'
import Checkbox from 'material-ui/Checkbox'
import './newSession.scss'
import ContentAdd from 'material-ui/svg-icons/content/add'
import DateTimePicker from 'material-ui-datetimepicker'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import moment from 'moment'
import PropTypes from 'prop-types'
import MenuItem from 'material-ui/MenuItem'
import _ from 'lodash'
import { browserHistory } from 'react-router'

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
      indexuserItemsArray: 0
    }
  }

  static propTypes = {
    getUsers: PropTypes.func,
    usersList: PropTypes.object,
    getRoles: PropTypes.func,
    rolesList: PropTypes.object,
    getStages: PropTypes.func,
    stagesList: PropTypes.object
    /* BĘDZIE POTRZEBNE JAK BACKEND ZROBI params: PropTypes.any */
  }

  componentWillMount () {
    this.props.getUsers(1)
    this.props.getRoles(1)
    this.props.getStages(1)
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

  createUserItem () {
    let item = [ ...this.state.userItemsArray ]
    item.push({
      id: this.state.indexuserItemsArray,
      userName: '',
      role: []
    })
    this.setState({
      userItemsArray: item,
      indexuserItemsArray: this.state.indexuserItemsArray + 1
    })
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

  handleChangeDropDownList (id, event, index, value) {
    console.log(value, id)
    let change = [ ...this.state.userItemsArray ]
    let checkIndex = _.findIndex(change, { id: id })
    if (checkIndex !== -1) {
      change[checkIndex].userName = value
    } else {
      change.push({
        id: id,
        userName: value
      })
    }
    this.setState({ userItemsArray: change })
  }

  handleChangeCheckbox (index, indexButton) {
    let change = [ ...this.state.userItemsArray ]
    let checkIndex = _.findIndex(change, { id: index })
    let isIn = _.findIndex(change[checkIndex].role, { id: indexButton })
    if (isIn !== -1) {
      change[checkIndex].role = _.remove(change[checkIndex].role, function (n) {
        return n.id !== indexButton
      })
    } else {
      change[checkIndex].role.push({ id: indexButton })
    }
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
    console.log('asdasdasdasddasdgsgsd', this.state.userItemsArray)
    return (
      <div className='main-container'>
        <div className='pages-box' style={{ height: '100%' }}>
          <div className='question-container'>
            <h2 style={{ display: 'inline-block' }}>New Session</h2>
            <div className={'buttons-obs'} style={{ float: 'right', display: 'inline-block' }}>
              <RaisedButton
                buttonStyle={{ width: '300px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Back to select the Trial'
                secondary
                icon={<FontIcon className='material-icons' style={{ margin: 0 }}>
                  <i className='material-icons'>keyboard_arrow_left</i></FontIcon>}
                onClick={this.back.bind(this)} />
            </div>
            <div style={{
              marginTop: 100 }} />
            <div className='col-md-5'
              style={{ display: 'flex', flexDirection: 'row', alignItems: 'center', paddingTop: 20 }}>
              <h3 style={{ paddingRight: 20 }}>Time:</h3>
              <DateTimePicker
                onChange={this.setDate}
                DatePicker={DatePickerDialog}
                TimePicker={TimePickerDialog}
                value={this.state.dateTime}
                textFieldStyle={{ width:200 }}
                format='YYYY-MM-DD kk:mm' />
            </div>
            <div className='col-md-7' style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
              <h3 style={{ paddingRight: 20, paddingTop: 20 }}>Start Stage:</h3>
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
            <h3 style={{ paddingTop: 180 }}>Users:</h3>
            {this.state.userItemsArray.map((object, index) => {
              return (
                <div key={index} style={{
                  display: 'flex',
                  alignItems: 'flex-end',
                  height: 40,
                  marginBottom: 50 }}>
                  <SelectField
                    value={object.userName}
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
                  <div className='col-xs-12 col-md-6'
                    style={{ display: 'flex', flexDirection: 'row', overflowX: 'scroll', width: '100%' }}>
                    {this.state.rolesList.map((user, indexChecked) => (
                      <Checkbox
                        key={indexChecked}
                        label={user.name}
                        labelStyle={{ width: 200, height: 30 }}
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
