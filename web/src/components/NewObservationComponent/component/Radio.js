import React from 'react'
import PropTypes from 'prop-types'
import { RadioButtonGroup, RadioButton } from 'material-ui'

export const Radio = (props) => {
  return (
    <RadioButtonGroup name='shipSpeed' defaultSelected={props.value}
      onChange={(event, value) => props.onChange(value)}
>
      {props.options.enumOptions.map(object => {
        return (<RadioButton
          value={object.value}
          label={object.label}
          disabled={props.disabled}
  />)
      })}
    </RadioButtonGroup>
  )
}
Radio.propTypes = {
  onChange: PropTypes.func,
  value: PropTypes.any,
  disabled: PropTypes.any,
  options: PropTypes.any
}

export default Radio
