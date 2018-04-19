import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './TrialManager.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'

class TrialManager extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrials: [],
      isLoading: false
    }
  }

  static propTypes = {
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.array
  }

  componentWillMount () {
    this.props.getTrials()
    // this.setState({ isLoading: true })
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.data &&
      nextProps.listOfTrials.data !== this.state.listOfTrials &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
        /** * MOCK * **/
      let data = {
        total:2,
        data:[
          { id:2,
            status:'ACTIVE',
            trialDescription:'Old building with 120 inhabitants started to burn because of failure' +
            'of electrical installations.' +
            'The fire is spreading very fast. One of inhabitants called for an external help. ',
            trialName:'Building Fire' }
        ]
      }
      this.setState({ listOfTrials: data.data })
      /** * MOCK * **/
      // this.setState({ listOfTrials: nextProps.listOfTrials.data, isLoading: false })
    }
  }

  viewTrial (id) {
    browserHistory.push(`/admin-trials/${id}`)
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
              <div className={'trial-select'}>Trial manager</div>
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
                  <AccordionItem disabled={object.status !== 'ACTIVE'}
                    title={<h3 className={'react-sanfona-item-title cursor-pointer'}>
                      {object.trialName}
                      <div className={'desc'}>{this.getShortDesc(object.trialDescription)}</div>
                    </h3>} expanded={false} >
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

export default TrialManager
