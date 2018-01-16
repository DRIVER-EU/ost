import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Menu from '../../components/Menu'
import './CoreLayout.scss'
import '../../styles/core.scss'
import { connect } from 'react-redux'

const styles = {
  height: '82%',
  marginTop: '70px',
  position: 'absolute',
  left: 'calc(1% + 224px)',
  right: '1%',
  overflowY: 'scroll',
  toastStyle: {
    backgroundColor: 'green',
    textAlign: 'center'
  },
  logo: {
    position: 'absolute',
    background: '#00022A'
  },
  menubox: {
    borderBottom: '1px solid #ffc300'
  }
}

class CoreLayout extends Component {
  constructor (props) {
    super()
    this.state = {
      openToast: false,
      toastDescription: '',
      user: 'Gawron',
      role: 'Admin'
    }
  }

  static propTypes = {
    children: PropTypes.element.isRequired
  }

  componentWillMount () {
    localStorage.removeItem('close-pathname')
    localStorage.removeItem('cancel-pathname')
    localStorage.removeItem('close-telescope-pathname')
    localStorage.removeItem('cancel-telescope-pathname')
  }

  handleRequestClose () {
    this.setState({ openToast: false, toastDescription: '' })
  }

  render () {
    return (
      <div className='core-container'>
        <div />
        <div className='view-container'>
          <div className='hidden-to-print' style={styles.menubox}>
            <Menu role={this.state.role} className='menu-layout' />
          </div>
          <div className='core-layout__viewport'>
            <div className='hidden-to-print' style={styles.menubox}/>
            {this.props.children}
          </div>
        </div>
      </div>
    )
  }
}
const mapDispatchToProps = {
}

const mapStateToProps = (state) => ({})

export default connect(mapStateToProps, mapDispatchToProps)(CoreLayout)
