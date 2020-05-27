import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './Trials.scss'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import { List, ListItem } from 'material-ui/List'
import IconInfo from 'material-ui/svg-icons/action/info'

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
    this.props.getTrials()
    let interval = setInterval(() => {
      this.props.getTrials()
    }, 5000)
    this.setState({
      interval: interval,
      isLoading: true
    })
  }

  componentWillUnmount () {
    clearInterval(this.state.interval)
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.listOfTrials.data &&
      nextProps.listOfTrials.data !== this.state.listOfTrials &&
      nextProps.listOfTrials.data !== this.props.listOfTrials) {
      this.setState({ listOfTrials: nextProps.listOfTrials.data, isLoading: false })
      if (!JSON.parse(localStorage.getItem('openTrial')) && nextProps.listOfTrials.data.length !== 0) {
        localStorage.setItem('openTrial', true)
        // browserHistory.push(`/trials/${nextProps.listOfTrials.data[0].id}/select-observation`)
        browserHistory.push(`/trials`)
      }
    }
  }

  viewTrial (id) {
    browserHistory.push(`/trials/${id}/select-observation`)
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
            <List style={{ width: '100%' }}>
              {this.state.listOfTrials && this.state.listOfTrials.map((object, index) => (
                <div
                  key={`${object.id}${index}`}
                  style={{
                    border: '1px solid #feb912',
                    backgroundColor: '#fafafa',
                    marginBottom: 5,
                    opacity: object.status !== 'ACTIVE' ? 0.4 : 1 }}>
                  <ListItem
                    disabled={object.status !== 'ACTIVE'}
                    leftIcon={<IconInfo color={'#fcb636'} />}
                    key={`${object.id}${index}`}
                    hoverColor='#C4D1DB'
                    primaryText={<div style={{ color: '#064c7b', fontSize: 24 }}>{object.trialName}</div>}
                    secondaryText={
                      <div style={{ color: '#064c7b', fontSize: 14 }}>
                        <div>session: {object.trialSessionName} stage: {object.name}</div>
                        <div>{this.getShortDesc(object.trialDescription)}</div>
                      </div>}
                    onClick={this.viewTrial.bind(this, object.id)}
                  />
                </div>
            ))}
            </List>
          </div>
        </div>
      </div>
    )
  }
}

export default Trials
