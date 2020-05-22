import React, { Component } from 'react'
import './TrialDetail.scss'
import TextField from 'material-ui/TextField'
import 'react-table/react-table.css'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import TabsWithTable from './TabsWithTable'
import PropTypes from 'prop-types'
import browserHistory from 'react-router/lib/browserHistory'

class TrialDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      trialId: '',
      trialName: '',
      description: '',
      sessionList: [],
      stageList: [],
      roleList: []
    }
  }
  static propTypes = {
    trialDetail: PropTypes.object,
    updateTrial: PropTypes.func,
    removeTrial: PropTypes.func,
    addNewTrial: PropTypes.func,
    newTrialDetail: PropTypes.object,
    removeBtn: PropTypes.any,
    tabs: PropTypes.any,
    params: PropTypes.object
  };

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
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  getTrialDetail = async(id) => {
    if (this.props.getTrialDetail) {
      await this.props.getTrialDetail(id)
    }
    let trialDetail = this.props.trialDetail
    if (this.props.trialDetail) {
      this.setState({
        trialName: trialDetail.trialName,
        description: trialDetail.trialDescription,
        sessionList: trialDetail.sessionSet,
        stageList: trialDetail.stageSet,
        roleList: trialDetail.roleSet
      })
    }
  }

  componentWillMount () {
    let id
    if (this.props.params) {
      id = this.props.params.id_trial
    }
    this.getTrialDetail(id)
  }

  componentDidMount () {
    let id
    if (this.props.params) {
      id = this.props.params.id_trial
    }
    id = parseInt(id)
    if (id) {
      this.setState({ trialId: id })
    }
  }
  render () {
    return (
      <div className='main-container'>
        <div className='pages-box'>
          <div className='trialDetail__container'>
            <div className='trialDetail__header'>
              <h1 className='header__text'>Trial</h1>
              <a
                style={{ cursor: 'pointer' }}
                onClick={() => browserHistory.push('/trial-manager')}
                className='header__link'>
                  Trial List
              </a>
            </div>
            <div className='trialDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={
                    this.state.trialId === ''
                      ? 'id will be given after saving'
                      : this.state.trialId
                  }
                  floatingLabelText='Id'
                  fullWidth
                  disabled
                  underlineShow={false}
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'trialName')}
                  value={this.state.trialName}
                  floatingLabelText='Name'
                  fullWidth
                />
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  addNewTrial={this.props.addNewTrial}
                  updateTrial={this.props.updateTrial}
                  id={this.state.trialId}
                  name={this.state.trialName}
                  description={this.state.description}
                  newTrialDetail={this.props.newTrialDetail}
                  inputsValue={[this.state.trialName, this.state.description]}
                  getTrialDetail={this.getTrialDetail}
                />
                {this.props.removeBtn && (
                  <RemoveBtn
                    id={this.state.trialId}
                    removeTrial={this.props.removeTrial}
                    sessionList={this.state.sessionList}
                    stageList={this.state.stageList}
                    roleList={this.state.roleList}
                  />
                )}
              </div>
            </div>
            <div>
              <TextField
                type='description'
                onChange={this.handleChangeInput.bind(this, 'description')}
                value={this.state.description}
                floatingLabelText='Description'
                fullWidth
                multiLine
                rowsMax={8}
              />
            </div>
            <div className='tabs__wrapper'>
              <TabsWithTable
                sessionList={this.state.sessionList}
                stageList={this.state.stageList}
                roleList={this.state.roleList}
                tabs={this.props.tabs}
                trialId={this.state.trialId}
              />
            </div>
          </div>
        </div>
      </div>
    )
  }
}
export default TrialDetail
