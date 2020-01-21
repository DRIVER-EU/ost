import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './TrialManager.scss'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'
import Spinner from 'react-spinkit'
import ReactTable from 'react-table'
import 'react-table/react-table.css'
import ErrorsModal from '../../ErrorsModal/component/ErrorsModal'
class TrialManager extends Component {
  constructor (props) {
    super(props)
    this.state = {
      listOfTrialsManager: [],
      listOfTrials: [],
      isLoading: false,
      openModal: false,
      trialId: null,
      selectedTrial: null,
      errorsModal: {
        show: false,
        warnings: [],
        errors: []
      }
    }
  }

  static propTypes = {
    getTrialManager: PropTypes.func,
    listOfTrialsManager: PropTypes.object,
    getListOfTrials: PropTypes.func,
    listOfTrials: PropTypes.object
  };

  handleCloseModal = () => {
    this.setState({
      errorsModal: {
        ...this.state.errorsModal,
        show: false
      }
    })
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
      let data = []
      for (let i = 0; i < nextProps.listOfTrialsManager.trialsSet.length; i++) {
        let trial = nextProps.listOfTrialsManager.trialsSet[i]
        if (!trial.archived) {
          data.push(trial)
        }
      }
      this.setState({
        listOfTrialsManager: data,
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
        `trial-manager/trial-detail/${this.state.selectedTrial.id}`
      )
    }
  }
  newTrial () {
    browserHistory.push(`trial-manager/new-trial`)
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
    this.setState({ isLoading: true })
    props.importFile(data).then(x => {
      this.setState({
        errorsModal: {
          errors: x.errors.map(y => y.message),
          warnings: x.warnings.map(y => y.message),
          show: x.errors.length !== 0 || x.warnings.length !== 0
        },
        isLoading: false
      })
    })
  };
  handleChangeDropDown (stateName, event, index, value) {
    let change = { ...this.state }
    change[stateName] = value
    this.setState(change)
  }

  render () {
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
                      onClick: e => {
                        this.setState({
                          selectedTrial: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewTrial()
                      }
                    }
                  } else {
                    return {}
                  }
                }}
                getTrProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      style: {
                        background: this.state.selectedTrial
                          ? rowInfo.original.id === this.state.selectedTrial.id
                            ? '#e5e5e5'
                            : ''
                          : '',
                        cursor: 'pointer',
                        display: rowInfo.original.archived
                          ? 'none'
                          : 'inline-flex'
                      }
                    }
                  }
                }}
              />
            )}
            <div className='action-btns'>
              <RaisedButton
                buttonStyle={{ width: '200px' }}
                backgroundColor='#244C7B'
                labelColor='#FCB636'
                label='+ New'
                onClick={this.newTrial.bind(this)}
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
              <ErrorsModal
                show={this.state.errorsModal.show}
                handleCloseModal={this.handleCloseModal}
                warnings={this.state.errorsModal.warnings}
                errors={this.state.errorsModal.errors}
              />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default TrialManager
