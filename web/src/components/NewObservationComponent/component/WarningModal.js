import React from 'react'
import PropTypes from 'prop-types'
import TextField from 'material-ui/TextField'
import Dialog from 'material-ui/Dialog'
import { RaisedButton } from 'material-ui'

export const inputValidationRegex = /(\t|\n|_|\||\[|\]|\{|\}|;)/g

export const checkInputs = (inputsValue) => {
  let isValid = true
  let suggestedText = ''
  const regex = inputValidationRegex
  for (let i = 0; i < inputsValue.length; i++) {
    const input = inputsValue[i]
    suggestedText = input.replace(regex, '')
    if (regex.test(input)) {
      isValid = false
      break
    } else {
      isValid = true
    }
  }
  return ({ isValid: isValid, suggestedText: suggestedText })
}

const warningModal = (props) => {
  const actions = [
    <RaisedButton
      style={{ position: 'absolute', left: '8px', bottom: '8px' }}
      backgroundColor='#FCB636'
      labelColor='#fff'
      label='Yes'
      type='Button'
      onClick={() => props.acceptSuggestedText()} />,
    <RaisedButton
      backgroundColor='#b71c1c'
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
        multiLine
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
