import React, { Component } from 'react'
import PropTypes from 'prop-types'
import ViewTrials from '../../../components/ViewTrials'

class ViewTrial extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getViewTrials: PropTypes.func,
    viewTrials: PropTypes.array
  }

  render () {
    return (
      <div className='background-home'>
        <ViewTrials
          getViewTrials={this.props.getViewTrials}
          viewTrials={this.props.viewTrials}
        />
      </div>
    )
  }
}

export default ViewTrial
