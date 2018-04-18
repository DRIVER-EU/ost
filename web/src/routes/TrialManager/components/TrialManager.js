import React, { Component } from 'react'
import PropTypes from 'prop-types'
import TrialManager from '../../../components/TrialManager'

class TrialManagerView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.array
  }

  render () {
    return (
      <div className='background-home'>
        <TrialManager
          getTrials={this.props.getTrials}
          listOfTrials={this.props.listOfTrials}
        />
      </div>
    )
  }
}

export default TrialManagerView
