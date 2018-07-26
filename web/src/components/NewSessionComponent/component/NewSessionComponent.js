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
      userItem: ''
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
    console.log(nextProps)
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

  handleChange = (event, index, value) => this.setState({ value });

  handleChangeDropDown (stateName, event, index, value) {
    let change = {}
    change[stateName] = value
    this.setState(change)
  }
  back = () => {
    browserHistory.push(`/trial-manager`)
  }
  setDate = (dateTime) => this.setState({ dateTime: moment(dateTime).format('DD-MM-YYYY hh:mm:ss') })

  render () {
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
            <div>
              <div style={{
                display: 'flex',
                alignItems: 'center' }}>
                <SelectField
                  value={this.state.userItem}
                  floatingLabelText='Select User'
                  onChange={this.handleChangeDropDown.bind(this, 'userItem')} >
                  {this.state.usersList !== undefined && this.state.usersList.map((index) => (
                    <MenuItem
                      key={index.id}
                      value={index.id}
                      style={{ color: 'grey' }}
                      primaryText={index.firstName + ' ' + index.lastName} />
                ))}
                </SelectField>
                <div className='col-xs-12 col-md-8' style={{ display: 'flex', flexDirection: 'row' }}>
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                  <Checkbox />
                </div>
              </div>
              <div style={{ float: 'right' }}>
                <FloatingActionButton secondary>
                  <ContentAdd />
                </FloatingActionButton>
              </div>
            </div>
            <RaisedButton
              label='Submit'
              style={{ display: 'table', margin: '0 auto', width: 200, marginTop: 120 }}
              primary />
          </div>
        </div>
      </div>
    )
  }
}

export default NewSessionComponent
