import React from 'react'
import PropTypes from 'prop-types'
import TextField from 'material-ui/TextField'
import Dialog from 'material-ui/Dialog'
import { RaisedButton } from 'material-ui'

const warningModal = (props) => {
  const actions = [
    <RaisedButton
      style={{ position: 'absolute', left: '8px', bottom: '8px' }}
      backgroundColor='#0f0'
      labelColor='#fff'
      label='Yes'
      type='Button'
      onClick={() => props.acceptSuggestedText()} />,
    <RaisedButton
      backgroundColor='#f00'
      labelColor='#fff'
      label={`No, I'll change it manually`}
      type='Button'
      onClick={() => props.closeWarningModal()} />
  ]
  return (
    <Dialog
      open={props.isWarningOpen}
      actions={actions} >
      <h4>The text contains invalid characters. Suggested text without these characters:</h4>
      <TextField
        disabled
        value={props.suggestedText}
        fullWidth />
      <h4>Do you accept these changes?</h4>
    </Dialog>
  )
}

warningModal.propTypes = {
  isWarningOpen: PropTypes.bool,
  suggestedText: PropTypes.string,
  closeWarningModal: PropTypes.func,
  acceptSuggestedText: PropTypes.func
}

export default warningModal
