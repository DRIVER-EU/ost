import React, { Component } from 'react'
import Form from 'react-jsonschema-form'
import DateComponent from '../../DateComponent/DateComponent'
import './Question.scss'

class Question extends Component {
  constructor (props) {
    super()
    this.state = {
      formData: { question1: { default1: 'test' } },
      schema : {
        'title': 'Obserwacja nr 1',
        'description': 'Jakiś opis tutaj.',
        'type': 'object',
        'properties': {
          'question1': {
            'type': 'object',
            'title': 'Boolean field',
            'properties': {
              'default1': {
                'type': 'string',
                'title': 'first 1'
              },
              'ndefaextult': {
                'type': 'boolean',
                'title': 'second 2'
              }
            }
          },
          'string': {
            'type': 'object',
            'title': 'String field',
            'properties': {
              'default': {
                'type': 'string',
                'title': 'text input (default)'
              }
            }
          },
          'selectWidgetOptions': {
            'title': 'Custom select widget with options',
            'type': 'string',
            'enum': [
              'foo',
              'bar'
            ],
            'enumNames': [
              'Foo',
              'Bar'
            ]
          }
        }
      },
      uiSchema: {
        'question1': {},
        'string': {
          'default1': {

          }
        },
        'secret': {
          'ui:widget': 'hidden'
        },
        'disabled': {
          'ui:disabled': true
        },
        'readonly': {
          'ui:readonly': true
        },
        'widgetOptions': {
          'ui:options': {
            'backgroundColor': 'yellow'
          }
        },
        'selectWidgetOptions': {
          'ui:options': {
            'backgroundColor': 'pink'
          }
        }
      }
    }
  }
  log (logged) {
    console.log(logged, this.state.formData)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='question-container'>
            <div className='trials-header'>
              <div>Summary of observations</div>
            </div>
            <DateComponent />
            <Form schema={this.state.schema}
              uiSchema={this.state.uiSchema}
              formData={this.state.formData}
              onChange={this.log.bind(this)}
              onSubmit={this.log.bind(this)}
              onError={this.log.bind(this)} />
          </div>
        </div>
      </div>
    )
  }
}

export default Question

