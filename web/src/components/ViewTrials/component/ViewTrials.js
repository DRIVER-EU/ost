import React, { Component } from 'react'
import PropTypes from 'prop-types'
import DateComponent from '../../DateComponent/DateComponent'
import './ViewTrials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentAdd from 'material-ui/svg-icons/content/add'

class ViewTrials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      viewTrials: []
    }
  }

  static propTypes = {
    getViewTrials: PropTypes.func,
    viewTrials: PropTypes.array,
    params: PropTypes.any
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

  viewEvent (id) {
    browserHistory.push(`/trials/${this.props.params.id}/question/${id}`)
  }

  newObservation () {
    browserHistory.push(`/trials/1/select-observation`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='view-trials-container'>
            <div className='trial-title'>
              <div>Trial:  New object spotted</div>
            </div>
            <div className='trials-header'>
              <div>List of events</div>
              <DateComponent />
            </div>
            <Accordion>
              {this.state.viewTrials.map((object) => {
                return (
                  <AccordionItem title={
                    <h3 className={'react-sanfona-item-title cursor-pointer' +
                      ((object.type !== 'message') ? ' observation' : ' message')}>
                      {object.title}
                      <div className={'time'}>{object.date}</div>
                    </h3>} expanded={false}>
                    <div>
                      <p>{object.description}</p>
                      { object.type !== 'message' &&
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewEvent.bind(this, object.id)}
                          label='View' />
                      </div>
                      }
                    </div>
                  </AccordionItem>
                )
              })}
            </Accordion>
            <FloatingActionButton onClick={this.newObservation.bind(this)} className={'observation-add'} secondary>
              <ContentAdd />
            </FloatingActionButton>
          </div>
        </div>
      </div>
    )
  }
}

export default ViewTrials
