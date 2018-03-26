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
    if (nextProps.listOfTrials.data &&
      nextProps.listOfTrials.data !== this.state.listOfTrials &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
      this.setState({ listOfTrials: nextProps.listOfTrials.data })
    }
  }

  viewTrial (id) {
    browserHistory.push(`/trials/${id}`)
  }

  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div className={'trial-select'}>Select trial</div>
            </div>
            <Accordion>
              {this.state.listOfTrials.map((object) => {
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
