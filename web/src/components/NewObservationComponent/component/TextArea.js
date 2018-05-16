import React from 'react'
import PropTypes from 'prop-types'
import { TextField } from 'material-ui'

export const TextArea = (props) => {
  return (

    <TextField
      onChange={(event, value) => props.onChange(value)}
      value={props.value}
      title={'sds'}
      disabled={props.disabled}
      fullWidth
      multiLine
          />
  )
}
TextArea.propTypes = {
  onChange: PropTypes.func,
  value: PropTypes.any,
  disabled: PropTypes.any
}

export default TextArea
