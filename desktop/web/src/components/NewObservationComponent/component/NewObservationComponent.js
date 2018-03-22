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
import Form from 'react-jsonschema-form-mui'

const styles = {
  checkbox: {
    marginBottom: 5
  }
}

const widgets = {
  Slider: (props) => {
    return Slider(props)
  }
}

class NewObservationComponent extends Component {
  constructor (props) {
    super()
    this.state = {
      title: 'Incident location shared',
      desc: `Note down this observation if indcident source is shared`,
      participants: [
        { id: 1, name: 'Participant 1' },
        { id: 2, name: 'Participant 2' }
      ],
      formData: {},
      questionSchema: {
        schema: {},
        uiSchema: {}
      },
      listOfParticipants: [],
      dateTime: null
    }
  }
  log (logged) {
    console.log(logged, this.state.model)
  }

  static propTypes = {
    getSchema: PropTypes.func,
    questionSchema: PropTypes.any
  }

  componentWillMount () {
    this.props.getSchema()
  }

  componentWillReceiveProps (nextProps) {
    console.log(nextProps.questionSchema)
    if (nextProps.questionSchema && this.props.questionSchema &&
        this.state.questionSchema !== nextProps.questionSchema) {
      let change = { ...this.state.questionSchema }
      change['schema'] = nextProps.questionSchema.schema
      change['uiSchema'] = nextProps.questionSchema.uiSchema
      this.setState({ questionSchema: change })
    }
  }

  submitObservation () {
    console.log(1, this.state.formData)
  }

  changeObservation (e, object) {
    this.setState({ formData: object.formData })
    console.log('change', object)
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
    if (this.state.participants.length > this.state.listOfParticipants.length) {
      change.splice(0, this.state.listOfParticipants.length)
      for (let i = 0; i < this.state.participants.length; i++) {
        change.push({ id: this.state.participants[i].id })
      }
    } else {
      change.splice(0, this.state.listOfParticipants.length)
    }
    this.setState({ listOfParticipants: change })
  }

  handleChecked (id) {
    let change = [ ...this.state.listOfParticipants ]
    let check = false
    let chosenIndex = _.findIndex(change, { id: id })
    if (chosenIndex !== -1) {
      check = true
    }
    return check
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='question-container'>
            <div className='trials-header'>
              <div>Observation</div>
            </div>
            <DateComponent />
            <p className='title-obs'>{this.state.title}</p>
            <p className='desc-obs'>{this.state.desc}</p>
            <p className='point-obs'>When:</p>
            <DateTimePicker
              onChange={this.setDate}
              DatePicker={DatePickerDialog}
              TimePicker={TimePickerDialog}
              format='YYYY/MM/DD H:mm' />
            <p className='point-obs'>Who:</p>
            {this.state.participants.map((object) => (
              <Checkbox
                label={object.name}
                checked={this.handleChecked(object.id)}
                onCheck={this.handleParticipants.bind(this, object.id)}
                style={styles.checkbox} />
              ))}
            <Checkbox
              label='All'
              checked={this.state.listOfParticipants.length === this.state.participants.length}
              onCheck={this.handleAllParticipants.bind(this)}
              style={styles.checkbox} />
            <p className='point-obs'>What:</p>
            <Form
              noValidate
              schema={this.state.questionSchema.schema}
              uiSchema={this.state.questionSchema.uiSchema}
              formData={this.state.formData}
              widgets={widgets}
              onChange={(value) => console.log(value)}
              onError={console.log('errors')} >
              <div className={'submit'}>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='Submit' />
              </div>
            </Form>
          </div>
        </div>
      </div>
    )
  }
}

export default NewObservationComponent

