import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './NewObservationComponent.scss'
import RaisedButton from 'material-ui/RaisedButton'
import Checkbox from 'material-ui/Checkbox'
import _ from 'lodash'
import DateTimePicker from 'material-ui-datetimepicker'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import Slider from './Slider'
import radio from './Radio'
import Form from 'react-jsonschema-form-mui'
import { browserHistory } from 'react-router'

const styles = {
  checkbox: {
    marginBottom: 5
  }
}

const widgets = {
  Slider: (props) => {
    return Slider(props)
  },
  radio: (props) => {
    return radio(props)
  }
}

class NewObservationComponent extends Component {
  constructor (props) {
    super()
    this.state = {
      observationForm: {
        name:'',
        description: '',
        schema: {},
        uiSchema: {},
        roles: [],
        formData: {}
      },
      listOfParticipants: [],
      dateTime: null
    }
  }

  static propTypes = {
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any
  }

  componentWillMount () {
    this.props.getSchema(this.props.params.id_observation, this.props.params.id)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.observationForm && this.props.observationForm &&
        this.state.observationForm !== nextProps.observationForm) {
      let change = { ...this.state.observationForm }
      change['schema'] = nextProps.observationForm.jsonSchema.schema
      change['uiSchema'] = nextProps.observationForm.jsonSchema.uiSchema
    //  change['uiSchema']['question_8'] = { 'ui:disabled': true, 'ui:widget': 'Radio', defaultSelected: '-2' }
      change['formData'] = nextProps.observationForm.jsonSchema.formData
     // change['formData']['question_8'] = '-2'
      this.setState({ observationForm: change })
    }
    if (nextProps.mode) {
      this.setState({ listOfParticipants: [1] })
    }
  }

  submitObservation () {
    let send = { ...this.state.formData }
    let tab = []
    send['dateTime'] = this.state.dateTime
    for (let i = 0; i < this.state.listOfParticipants.length; i++) {
      tab.push(this.state.listOfParticipants[i].id)
    }
    send['listOfParticipants'] = tab
  }

  changeObservation (object) {
    this.setState({ formData: object.formData })
  }

  setDate = (dateTime) => this.setState({ dateTime })

  handleParticipants (id) {
    let change = [ ...this.state.listOfParticipants ]
    let chosenIndex = _.findIndex(change, { id: id })
    if (chosenIndex === -1) {
      change.push({ id: id })
    } else {
      change.splice(chosenIndex, 1)
    }
    this.setState({ listOfParticipants: change })
  }

  handleAllParticipants () {
    let change = [ ...this.state.listOfParticipants ]
    if (this.state.observationForm.roles.length > this.state.listOfParticipants.length) {
      change.splice(0, this.state.listOfParticipants.length)
      for (let i = 0; i < this.state.observationForm.roles.length; i++) {
        change.push({ id: this.state.observationForm.roles[i].id })
      }
    } else {
      change.splice(0, this.state.listOfParticipants.length)
    }
    this.setState({ listOfParticipants: change })
  }

  handleChecked (id) {
    let change = [ ...this.state.listOfParticipants ]
    let check = false
    let chosenIndex = ''
    if (this.props.mode) {
      let tab = []
      change.map((id) => (
        tab.push({ id: id })
      ))
      chosenIndex = _.findIndex(tab, { id: id })
    } else {
      chosenIndex = _.findIndex(change, { id: id })
    }
    if (chosenIndex !== -1) {
      check = true
    }
    return check
  }

  back () {
    browserHistory.push(`/trials/${this.props.params.id}`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='question-container'>
            <div className='trials-header'>
              <DateComponent />
              {this.props.mode !== 'new'
              ? <div style={{ textAlign:'center', borderBottom: '1px solid #feb912' }}>Observation</div>
              : <div style={{ textAlign:'center', borderBottom: '1px solid #feb912' }}>New observation</div>
            }
            </div>
            <p className='title-obs'>{this.state.observationForm.name}</p>
            <p className='desc-obs'>{this.state.observationForm.description}</p>
            <p className='point-obs'>When:</p>
            <DateTimePicker
              disabled={this.props.mode !== 'new'}
              onChange={this.setDate}
              DatePicker={DatePickerDialog}
              TimePicker={TimePickerDialog}
              format='YYYY/MM/DD H:mm' />
            {this.state.observationForm.roles.length !== 0 && <div>
              <p className='point-obs'>Who:</p>
              {this.state.observationForm.roles.map((object) => (
                <Checkbox
                  disabled={this.props.mode !== 'new'}
                  label={object.name}
                  checked={this.handleChecked(object.id)}
                  onCheck={this.handleParticipants.bind(this, object.id)}
                  style={styles.checkbox} />
              ))}
              {this.state.observationForm.roles.length > 2 &&
              <Checkbox
                disabled={this.props.mode !== 'new'}
                label='All'
                checked={this.state.listOfParticipants.length === this.state.observationForm.roles.length}
                onCheck={this.handleAllParticipants.bind(this)}
                style={styles.checkbox} />
            }
            </div>
            }
            <p className='point-obs'>What:</p>
            <Form
              noValidate
              schema={this.state.observationForm.schema}
              uiSchema={this.state.observationForm.uiSchema}
              formData={this.state.observationForm.formData}
              widgets={widgets}
              onChange={(value) => this.changeObservation(value)} >
              <div className={'buttons-center'}>
                {this.props.mode !== 'viewAdmin' &&
                <div className={'buttons-observation'}>
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor='#244C7B'
                    labelColor='#FCB636'
                    label='Back'
                    secondary
                    onClick={this.back.bind(this)}
                  />
                </div>
                }
                {this.props.mode === 'new' &&
                <div className={'submit buttons-observation'}>
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor='#244C7B'
                    labelColor='#FCB636'
                    label='Submit'
                    onClick={this.submitObservation.bind(this)} />
                </div>
                }
              </div>
            </Form>
          </div>
        </div>
      </div>
    )
  }
}

export default NewObservationComponent

