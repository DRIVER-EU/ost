import React from 'react'
import PropTypes from 'prop-types'
import SliderUI from 'material-ui/Slider'

export const Slider = (props) => {
  props.onChange(props.value ? props.value : props.schema.value)
  return (
    <div>
      <SliderUI
        value={props.value ? props.value : props.schema.value}
        required={props.required}
        onChange={(event, value) => props.onChange(value)}
        min={props.schema.min}
        max={props.schema.max}
        step={props.schema.step}
        disabled={props.disabled}
        />
      <div style={{
        marginTop: '-35px',
        textAlign: 'center',
        fontWeight: 'bold'
      }}>{props.value}</div>
    </div>
  )
}
Slider.propTypes = {
  schema: PropTypes.any,
  required: PropTypes.any,
  onChange: PropTypes.func,
  value: PropTypes.any,
  disabled: PropTypes.any
}

export default Slider
