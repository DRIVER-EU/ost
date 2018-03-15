import React, { Component } from 'react'
import { SchemaForm } from 'react-schema-form'
import DateComponent from '../../DateComponent/DateComponent'
import './Question.scss'
import RaisedButton from 'material-ui/RaisedButton'
import Checkbox from 'material-ui/Checkbox'
import _ from 'lodash'
import DateTimePicker from 'material-ui-datetimepicker'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import Slider from 'material-ui/Slider'
import ComposedComponent from 'react-schema-form/lib/ComposedComponent'

const styles = {
  checkbox: {
    marginBottom: 5
  }
}

let mapper = {
  'rc-slider': ComposedComponent(Slider)
}

class Question extends Component {
  constructor (props) {
    super()
    this.state = {
      title: 'Incident location shared',
      desc: `Lorem ipsum dolor sit amet, consectetur adipiscing elit,
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.`,
      participants: [
        { id: 1, name: 'Amadeusz' },
        { id: 3, name: 'Janusz' },
        { id: 4, name: 'Andrzej' },
        { id: 6, name: 'Mikołaj' }
      ],
      form: [
        'string',
        'anotherstring',
        {
          'key': 'slider',
          'type': 'rc-slider'
        },
        'number',
        'boolean',
        {
          'key':'dropdown',
          'validationMessage': 'Select at least one value',
          'type':'select',
          'titleMap':[
            { 'value':'A', 'name':'A' },
            { 'value':'B', 'name':'B' },
            { 'value':'C', 'name':'C' }
          ]
        },
        {
          'key': 'radios',
          'type': 'radios',
          'titleMap': [
            {
              'value': 'c',
              'name': 'C'
            },
            {
              'value': 'b',
              'name': 'B'
            },
            {
              'value': 'a',
              'name': 'A'
            }
          ]
        },
        'date',
        'comment'
      ],
      schema: {
        'type': 'object',
        'properties': {
          'string': {
            'type': 'string',
            'minLength': 3
          },
          'anotherstring': {
            'type': 'string',
            'minLength': 3
          },
          'slider': {
            'description': 'Slider',
            'type': 'rc-slider'
          },
          'number': {
            'type': 'number'
          },
          'boolean': {
            'type': 'boolean'
          },
          'date': {
            'title': 'Date',
            'type': 'date'
          },
          'comment': {
            'title': 'Comment',
            'type': 'string',
            'maxLength': 20,
            'validationMessage': "Don't be greedy!",
            'description': 'Please write your comment here.'
          }
        },
        'required': [
          'number',
          'comment'
        ]
      },
      model: {},
      listOfParticipants: [],
      dateTime: null
    }
  }
  log (logged) {
    console.log(logged, this.state.model)
  }

  submitObservation () {

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
            <SchemaForm
              schema={this.state.schema}
              form={this.state.form}
              model={this.state.model}
              onModelChange={this.log.bind(this)}
              mapper={mapper} />
            <div className={'submit'}>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                onClick={this.submitObservation.bind(this)}
                label='Submit' />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Question

