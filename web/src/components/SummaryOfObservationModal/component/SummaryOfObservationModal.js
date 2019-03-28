import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './SummaryOfObservationModal.scss'
import Dialog from 'material-ui/Dialog'
import FlatButton from 'material-ui/FlatButton'
import NewObservationComponent from '../../../components/NewObservationComponent'
class SummaryOfObservationModal extends Component {
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
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any,
    getSchema: PropTypes.func,
    getSchemaView: PropTypes.func,
    downloadFile: PropTypes.func,
    sendObservation: PropTypes.func
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
        actions={actions}
        modal={false}
        open={this.props.show}
        onRequestClose={() => this.props.handleShowModal()}
        bodyClassName={'content-schema'}
        bodyStyle={{ padding: 0, maxHeight: '100%', color: 'rgb(40, 40, 41)' }}
        contentStyle={{ maxHeight: '100%' }}
        >
        {this.props.mode === 'usermodal'
        ? <NewObservationComponent
          getSchema={this.props.getSchema}
          observationForm={this.props.observationForm}
          mode={'profileQuestion'}
          params={this.state.newParams}
          sendObservation={this.props.sendObservation}
          closeModal={this.props.handleShowModal} />
        : <NewObservationComponent
          getSchema={this.props.getSchemaView}
          observationForm={this.props.observationForm}
          mode={'viewAdmin'}
          params={this.state.newParams}
          downloadFile={this.props.downloadFile} />
        }
      </Dialog>
    )
  }
}

export default SummaryOfObservationModal
