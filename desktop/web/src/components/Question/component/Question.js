import React, { Component } from 'react'
import { SchemaForm } from 'react-schema-form'
import DateComponent from '../../DateComponent/DateComponent'
import './Question.scss'
import RaisedButton from 'material-ui/RaisedButton'

class Question extends Component {
  constructor (props) {
    super()
    this.state = {
      form: [
        'string',
        'anotherstring',
        'integer',
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
        'title': 'Incident location shared',
        'description': 'A simple form example.',
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
          'integer': {
            'type': 'integer'
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
      model: {}
    }
  }
  log (logged) {
    console.log(logged, this.state.model)
  }

  submitObservation () {

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
            <SchemaForm
              schema={this.state.schema}
              form={this.state.form}
              model={this.state.model}
              onModelChange={this.log.bind(this)} />
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

