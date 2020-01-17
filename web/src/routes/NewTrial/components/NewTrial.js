import React, { Component } from 'react'
import PropTypes from 'prop-types'
import TrialDetail from '../../../components/TrialDetail'

class NewTrialWrapper extends Component {
  constructor (props) {
    super(props)
    this.state = {}
  }

  static propTypes = {
    addNewTrial: PropTypes.func,
    newTrialDetail: PropTypes.object
  };
  render () {
    return (
      <TrialDetail
        removeBtn={false}
        tabs={false}
        addNewTrial={this.props.addNewTrial}
        newTrialDetail={this.props.newTrialDetail}
      />
    )
  }
}
export default NewTrialWrapper
