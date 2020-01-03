import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './TrialManager.scss'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import ReactTable from 'react-table'
import 'react-table/react-table.css'

class TrialManager extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrialsManager: [],
      listOfTrials: [],
      isLoading: false,
      openModal: false,
      trialId: null,
      selectedTrial: null
    }
  }

  static propTypes = {
    getTrialManager: PropTypes.func,
    listOfTrialsManager: PropTypes.object,
    getListOfTrials: PropTypes.func,
    listOfTrials: PropTypes.object
  };

  componentWillMount () {
    this.props.getTrialManager()
    this.setState({ isLoading: true })
  }

  componentWillReceiveProps (nextProps) {
    if (
      nextProps.listOfTrialsManager.trialsSet &&
      nextProps.listOfTrialsManager.trialsSet !==
      this.state.listOfTrialsManager &&
      nextProps.listOfTrialsManager.trialsSet !==
      this.props.listOfTrialsManager.trialsSet
    ) {
      this.setState({
        listOfTrialsManager: nextProps.listOfTrialsManager.trialsSet,
        isLoading: false
      })
    }
    if (
      nextProps.listOfTrials &&
      nextProps.listOfTrials !== this.props.listOfTrials
    ) {
      let listOfTrials = []
      for (let key in nextProps.listOfTrials) {
        listOfTrials.push({ id: key, name: nextProps.listOfTrials[key] })
      }
      this.setState({ listOfTrials })
    }
  }

  viewTrial () {
    if (this.state.selectedTrial) {
      browserHistory.push(
        `trial-manager/${this.state.selectedTrial.id}/admin-trials/${this.state.selectedTrial.id}`
      )
    }
  }

  getShortDesc (str) {
    let desc = str.split(/[.|!|?]\s/)
    if (desc[1]) {
      return desc[0] + '. ' + desc[1]
    } else {
      return desc[0]
    }
  }

  handleOpen = () => {
    this.props.getListOfTrials()
    this.setState({
      trialId: null,
      openModal: true
    })
  };

  handleClose = () => {
    this.setState({ openModal: false })
  };

  newSession = () => {
    if (this.state.trialId) {
      browserHistory.push(`trial-manager/${this.state.trialId}/newsession`)
    }
  };

  importFileAction = (el, props) => {
    const data = new FormData()
    data.append('multipartFile', el.target.files[0])
    props.importFile(data)
  };
  handleChangeDropDown (stateName, event, index, value) {
    let change = { ...this.state }
    change[stateName] = value
    this.setState(change)
  }

  render () {
    // const actions = [
    //   <FlatButton label="Cancel" primary onTouchTap={this.handleClose} />,
    //   <FlatButton
    //     label="Next"
    //     secondary
    //     keyboardFocused
    //     onClick={this.newSession.bind(this)}
    //   />
    // ];
    const columns = [
      {
        Header: 'Trials',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 100
          },
          {
            Header: 'Name',
            accessor: 'name'
          }
        ]
      }
    ]
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trials-container'>
            <div className='trials-header'>
              <div className={'trial-select'}>Trial List</div>
            </div>
            <div className='trials-btn'>
              <input
                id='file'
                type='file'
                name='file'
                ref={ref => (this.upload = ref)}
                style={{ display: 'none' }}
                onChange={el => this.importFileAction(el, this.props)}
              />
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='Import Trials'
                onClick={e => this.upload.click()}
                type='Button'
              />
            </div>
            {this.state.isLoading && (
              <div className='spinner-box'>
                <div className={'spinner'}>
                  <Spinner
                    fadeIn='none'
                    className={'spin-item'}
                    color={'#fdb913'}
                    name='ball-spin-fade-loader'
                  />
                </div>
              </div>
            )}
            {!this.state.isLoading &&
              this.state.listOfTrialsManager.length === 0 && (
                <div className={'no-sessions'}>No trial sessions available</div>
              )}
            {this.state.listOfTrialsManager.length > 0 && (
              <ReactTable
                data={this.state.listOfTrialsManager}
                columns={columns}
                multiSort
                showPagination={false}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: (e) => {
                        this.setState({
                          selectedTrial: rowInfo.original
                        })
                      },
                      style: {
                        background: this.state.selectedTrial ? rowInfo.original.id ===
                          this.state.selectedTrial.id ? '#e5e5e5' : '' : '',
                        cursor: 'pointer'
                      }
                    }
                  } else {
                    return {}
                  }
                }}
              />
            )}
            {/* <FloatingActionButton
              className={'observation-add'}
              style={{ float: 'right' }}
              onTouchTap={this.handleOpen}
              secondary>
              <ContentAdd />
            </FloatingActionButton> */}
            <div className='action-btns'>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='+ New'
                type='Button'
              />
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor={this.state.selectedTrial ? '#FCB636' : '#ccc'}
                labelColor='#fff'
                label='Edit'
                type='Button'
                disabled={!this.state.selectedTrial}
                onClick={this.viewTrial.bind(this)}
              />
            </div>
          </div>
        </div>
        {/* <Dialog
          title="Select Trial"
          actions={actions}
          modal={false}
          open={this.state.openModal}
          onRequestClose={this.handleClose}
        >
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              alignItems: "center",
              justifyContent: "center",
              width: 600,
              marginBottom: 10
            }}
          >
            <h2
              style={{
                display: "inline-block",
                padding: "50px 40px 55px 50px"
              }}
            >
              Trial:
            </h2>
            <SelectField
              value={this.state.trialId}
              floatingLabelText="Select Trial"
              onChange={this.handleChangeDropDown.bind(this, "trialId")}
            >
              {this.state.listOfTrials &&
                this.state.listOfTrials.length !== 0 &&
                this.state.listOfTrials.map(index => (
                  <MenuItem
                    key={index.id}
                    value={index.id}
                    style={{ color: "grey" }}
                    primaryText={index.name}
                  />
                ))}
            </SelectField>
          </div>
        </Dialog> */}
      </div>
    )
  }
}

export default TrialManager
