import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SummaryOfObservationModal.scss'
import Dialog from 'material-ui/Dialog'
import FlatButton from 'material-ui/FlatButton'
import NewObservationComponent from '../../../components/NewObservationComponent'

class AdminTrials extends Component {
  constructor (props) {
    super(props)
    this.state = {
      newParams: {}
    }
  }

  static propTypes = {
    show: PropTypes.bool,
    object: PropTypes.object,
    handleShowModal: PropTypes.func,
    getSchema: PropTypes.func,
    observationForm: PropTypes.any,
    params: PropTypes.any
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.params && nextProps.object &&
        nextProps.params !== this.state.newParams.id &&
        nextProps.object.id !== this.state.newParams.id_observation) {
      this.setState({ newParams: { id: nextProps.params, id_observation: nextProps.object.id } })
    }
    if (this.props.object && this.props.params) {
        // eslint
    }
  }

  render () {
    const actions = [
      <FlatButton
        label='Close'
        primary
        onClick={() => this.props.handleShowModal()}
          />
    ]
    return (
      <Dialog
        title='Scrollable Dialog'
        actions={actions}
        modal={false}
        open={this.props.show}
        onRequestClose={() => this.props.handleShowModal()}
        autoScrollBodyContent
        bodyClassName={'content-schema'}
        bodyStyle={{ 'padding': '36px 0 0' }}
        >
        <NewObservationComponent
          getSchema={this.props.getSchema}
          observationForm={this.props.observationForm}
          mode={'viewAdmin'}
          params={this.state.newParams} />
      </Dialog>
    )
  }
}

export default AdminTrials
