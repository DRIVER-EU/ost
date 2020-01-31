import React, { Component } from 'react'
import RaisedButton from 'material-ui/RaisedButton'
import ReactTable from 'react-table'
import PropTypes from 'prop-types'
import 'react-table/react-table.css'
import Dialog from 'material-ui/Dialog'
import TextField from 'material-ui/TextField'
import FlatButton from 'material-ui/FlatButton'

class Option extends Component {
  constructor (props) {
    super(props)
    this.state = {
      selectedOption: null,
      openRemoveDialog: false,
      openAddOptionDialog: false,
      name: '',
      position: ''
    }
  }
  static propTypes = {
    new: PropTypes.bool,
    option: PropTypes.array,
    questionDetailId: PropTypes.any
  }
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  async addNewOption (option) {
    if (this.props.addOption) {
      await this.props.addOption(option)
      this.handleCloseDialog('openAddOptionDialog')
      if (this.props.getQuestion) {
        this.props.getQuestion(this.props.questionDetailId)
      }
    }
  }
  async removeOption (option) {
    await this.props.removeOption(option.id)
    this.handleCloseDialog('openRemoveDialog')
    if (this.props.getQuestion) {
      this.props.getQuestion(this.props.questionDetailId)
    }
  }
  handleOpenDialog (name) {
    let change = {}
    change[name] = true
    this.setState(change)
  }

  handleCloseDialog (name) {
    let change = {}
    change[name] = false
    this.setState(change)
  }
  render () {
    const option = {
      name: this.state.name,
      position: this.state.position,
      questionId: this.props.questionDetailId
    }
    const actionsRemoveDialog = [
      <FlatButton
        label='No'
        primary
        onClick={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
        />,
      <RaisedButton
        backgroundColor='#b71c1c'
        labelColor='#fff'
        label='Yes'
        type='Button'
        onClick={this.removeOption.bind(this, this.state.selectedOption)}
        />
    ]
    const actions = [
      <FlatButton label='Cancel' primary onClick={this.handleCloseDialog.bind(this, 'openAddOptionDialog')} />,
      <RaisedButton
        backgroundColor='#FCB636'
        labelColor='#fff'
        label='Add'
        type='Button'
        onClick={this.addNewOption.bind(this, option)}
        />
    ]
    const columns = [
      {
        Header: 'Option',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 100
          },
          {
            Header: 'Value',
            accessor: 'name'
          },
          {
            Header: 'Position',
            accessor: 'position'
          }
        ]
      }
    ]

    return (
      <div>
        <div className='table__wrapper'>
          <ReactTable
            data={this.props.option}
            columns={columns}
            multiSort
            showPagination={false}
            minRows={0}
            getTdProps={(state, rowInfo) => {
              if (rowInfo && rowInfo.row) {
                return {
                  onClick: e => {
                    this.setState({
                      selectedOption: rowInfo.original
                    })
                  },
                  style: {
                    background: this.state.selectedOption
                          ? rowInfo.original.id ===
                            this.state.selectedOption.id
                            ? '#e5e5e5'
                            : ''
                          : '',
                    cursor: 'pointer'
                  }
                }
              }
            }}
              />
          {!this.props.new && (
            <div className='action-btns'>
              <div>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor='#244C7B'
                  labelColor='#FCB636'
                  label='+ New'
                  type='Button'
                  onClick={this.handleOpenDialog.bind(this, 'openAddOptionDialog')}
                  />
                <Dialog
                  title='Add Option'
                  actions={actions}
                  contentClassName='custom__dialog'
                  modal={false}
                  open={this.state.openAddOptionDialog}
                  onRequestClose={this.handleClose}
                     >
                  <TextField
                    type='name'
                    onChange={this.handleChangeInput.bind(this, 'name')}
                    value={this.state.name}
                    floatingLabelText='Name'
                    fullWidth
                />
                  <TextField
                    type='position'
                    onChange={this.handleChangeInput.bind(this, 'position')}
                    value={this.state.position}
                    floatingLabelText='Position'
                    fullWidth
                />
                </Dialog>
              </div>
              <div>
                <RaisedButton
                  buttonStyle={{ width: '200px' }}
                  backgroundColor={
                      this.state.selectedOption ? '#b71c1c' : '#ccc'
                    }
                  labelColor='#fff'
                  label='Remove'
                  type='Button'
                  disabled={!this.state.selectedOption}
                  onClick={this.handleOpenDialog.bind(this, 'openRemoveDialog')}
                  />
                <Dialog
                  title='Do you want to remove option?'
                  actions={actionsRemoveDialog}
                  modal={false}
                  contentClassName='custom__dialog'
                  open={this.state.openRemoveDialog}
                  onRequestClose={this.handleCloseDialog.bind(this, 'openRemoveDialog')}
                   />
              </div>
            </div>
              )}
        </div>
      </div>
    )
  }
}
export default Option
