import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Dialog from 'material-ui/Dialog'
import './ErrorsModal.scss'
import { RaisedButton } from 'material-ui'
class ErrorsModal extends Component {

  static propTypes = {
    show: PropTypes.bool,
    errors: PropTypes.array,
    warnings: PropTypes.array,
    handleCloseModal: PropTypes.func
  };

  buildList = items => {
    return (
      <ul>
        {items.map((x, i) => (
          <li key={i}>{x}</li>
        ))}
      </ul>
    )
  };

  render () {
    const actions = [
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Close'
        type='Button'
        onClick={() => this.props.handleCloseModal()}
      />
    ]
    return (
      <Dialog
        actions={actions}
        modal={false}
        open={this.props.show}
        onRequestClose={() => this.props.handleCloseModal()}
        contentClassName='custom__dialog'
        bodyClassName={'content-schema'}
        bodyStyle={{
          maxHeight: '100%',
          color: 'rgb(40, 40, 41)',
          padding: 10
        }}
        contentStyle={{ maxHeight: '100%' }}
      >
        <div className='modalContainer'>
          {this.props.warnings.length !== 0 ? (
            <div>
              <div className='titleContainer'>
                <div className='title'>Warnings</div>
              </div>
              <div className='warningsContainer'>
                {this.buildList(this.props.warnings)}
              </div>
            </div>
          ) : (
            <div />
          )}
          {this.props.errors.length !== 0 ? (
            <div>
              <div className='titleContainer'>
                <div className='title'>Errors</div>
              </div>
              <div className='errorsContainer'>
                {this.buildList(this.props.errors)}
              </div>
            </div>
          ) : (
            <div />
          )}
        </div>
      </Dialog>
    )
  }
}

export default ErrorsModal
