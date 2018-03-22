import React, { Component } from 'react'
import PropTypes from 'prop-types'
import NewObservationComponent from '../../../components/NewObservationComponent'

class NewObservation extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    getSchema: PropTypes.func,
    questionSchema: PropTypes.any
  }

  render () {
    return (
      <div className='background-home'>
        <NewObservationComponent
          getSchema={this.props.getSchema}
          questionSchema={this.props.questionSchema} />
      </div>
    )
  }
}

export default NewObservation
