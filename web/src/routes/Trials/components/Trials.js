import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Trials from '../../../components/Trials'

class TrialsView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getTrials: PropTypes.func,
    listOfTrials: PropTypes.object
  }

  render () {
    return (
      <div className='background-home'>
        <Trials
          getTrials={this.props.getTrials}
          listOfTrials={this.props.listOfTrials}
        />
      </div>
    )
  }
}

export default TrialsView
