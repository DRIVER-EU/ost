import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './ViewTrials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'

class ViewTrials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      viewTrials: []
    }
  }

  static propTypes = {
    getViewTrials: PropTypes.func,
    viewTrials: PropTypes.array
  }

  componentWillMount () {
    this.props.getViewTrials()
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.viewTrials.data &&
      nextProps.viewTrials.data !== this.state.viewTrials &&
      nextProps.viewTrials.data !== this.props.viewTrials) {
      this.setState({ viewTrials: nextProps.viewTrials.data })
    }
  }

  viewTrial (id) {
    browserHistory.push(`/trials`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
            <div className='trials-header'>
              <div>List of events</div>
              <DateComponent />
            </div>
            <Accordion>
              {this.state.viewTrials.map((object) => {
                return (
                  <AccordionItem title={object.title} expanded={false}>
                    <div>
                      <p>{object.description}</p>
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewTrial.bind(this, object.id)}
                          label='Ok' />
                      </div>
                    </div>
                  </AccordionItem>
                )
              })}
            </Accordion>
          </div>
        </div>
      </div>
    )
  }
}

export default ViewTrials
