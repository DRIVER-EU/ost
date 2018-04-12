import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './Trials.scss'
import { Accordion, AccordionItem } from 'react-sanfona'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'

class Trials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrials: []
    }
  }

  static propTypes = {
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.array
  }

  componentWillMount () {
    this.props.getTrials()
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.content &&
      nextProps.listOfTrials.content !== this.state.listOfTrials &&
      nextProps.listOfTrials.content !== this.props.listOfTrials) {
      this.setState({ listOfTrials: nextProps.listOfTrials.content })
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
              <div className={'trial-select'}>Select trial</div>
            </div>
            {this.state.listOfTrials.length === 0 && <div> No trial sessions available</div>}
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

export default Trials
