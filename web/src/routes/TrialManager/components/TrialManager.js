import React, { Component } from 'react'
import PropTypes from 'prop-types'
import TrialManager from '../../../components/TrialManager'

class TrialManagerView extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getTrialManager: PropTypes.func,
    listOfTrialsManager: PropTypes.object,
    getListOfTrials: PropTypes.func,
    importFile: PropTypes.func,
    listOfTrials: PropTypes.object
  }

  render () {
    return (
      <div className='background-home'>
        <TrialManager
          getTrialManager={this.props.getTrialManager}
          listOfTrialsManager={this.props.listOfTrialsManager}
          getListOfTrials={this.props.getListOfTrials}
          importFile={this.props.importFile}
          listOfTrials={this.props.listOfTrials}
        />
      </div>
    )
  }
}

export default TrialManagerView
