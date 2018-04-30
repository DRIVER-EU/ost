import React from 'react'
import PropTypes from 'prop-types'
import { RadioButtonGroup, RadioButton } from 'material-ui'

export const Radio = (props) => {
  props.onChange(props.value ? props.value : props.schema.value)
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
  schema: PropTypes.any,
  onChange: PropTypes.func,
  value: PropTypes.any,
  disabled: PropTypes.any,
  options: PropTypes.any
}

export default Radio
