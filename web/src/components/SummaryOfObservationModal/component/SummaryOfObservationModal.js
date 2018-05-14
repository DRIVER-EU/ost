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
    observationForm: PropTypes.any,
    mode: PropTypes.string,
    params: PropTypes.any,
    getSchema: PropTypes.func,
    getSchemaView: PropTypes.func
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
        bodyStyle={{ padding: 0, maxHeight: '100%' }}
        contentStyle={{ maxHeight: '100%' }}
        >
        {this.props.mode === 'usermodal'
        ? <NewObservationComponent
          getSchema={this.props.getSchema}
          observationForm={this.props.observationForm}
          mode={'profileQuestion'}
          params={this.state.newParams} />
        : <NewObservationComponent
          getSchema={this.props.getSchemaView}
          observationForm={this.props.observationForm}
          mode={'viewAdmin'}
          params={this.state.newParams} />
        }
      </Dialog>
    )
  }
}

export default AdminTrials
