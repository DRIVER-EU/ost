import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './Trials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'

class Trials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrials: [],
      isLoading: false,
      interval: ''
    }
  }

  static propTypes = {
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.object
  }

  componentWillMount () {
    let interval = setInterval(() => {
      this.props.getTrials()
    }, 5000)
    this.setState({
      interval: interval,
      isLoading: true
    })
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.data &&
      nextProps.listOfTrials.data !== this.state.listOfTrials &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
      this.setState({ listOfTrials: nextProps.listOfTrials.data, isLoading: false })
      localStorage.setItem('listOfTrials', JSON.stringify(nextProps.listOfTrials))
      if (!JSON.parse(localStorage.getItem('openTrial')) && nextProps.listOfTrials.data.length !== 0) {
        localStorage.setItem('openTrial', true)
        browserHistory.push(`/trials/${nextProps.listOfTrials.data[0].id}/select-observation`)
      }
    }
  }

  viewTrial (id) {
    browserHistory.push(`/trials/${id}`)
  }

  getShortDesc (str) {
    let desc = str.split(/[.|!|?]\s/)
    if (desc[1]) {
      return desc[0] + '. ' + desc[1]
    } else {
      return desc[0]
    }
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div className={'trial-select'}>Trial sessions</div>
            </div>
            {this.state.isLoading && <div className='spinner-box'>
              <div className={'spinner'}>
                <Spinner fadeIn='none' className={'spin-item'} color={'#fdb913'} name='ball-spin-fade-loader' />
              </div>
              </div>
            }
            {(!this.state.isLoading && this.state.listOfTrials.length === 0) &&
            <div className={'no-sessions'}> No trial sessions available</div>}
            <Accordion>
              {this.state.listOfTrials.map((object) => {
                return (
                  <AccordionItem key={object.id} disabled={object.status !== 'ACTIVE'}
                    title={<div className={'react-sanfona-item-title cursor-pointer'}><h3>
                      {object.trialName}</h3>
                      <h5 style={{ margin: '4px 0 10px' }}>
                        session: #{object.id} stage: {object.name}
                      </h5>
                      <div className={'desc'}>{this.getShortDesc(object.trialDescription)}</div>
                    </div>} expanded={false} >
                    <div>
                      <p>{object.trialDescription}</p>
                      <div style={{ display: 'table', margin: '0 auto' }}>
                        <RaisedButton
                          buttonStyle={{ width: '200px' }}
                          backgroundColor='#244C7B'
                          labelColor='#FCB636'
                          onClick={this.viewTrial.bind(this, object.id)}
                          label='Enter' />
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

export default Trials
