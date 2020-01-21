import React, { Component } from 'react'
import PropTypes from 'prop-types'
import './StageDetail.scss'
import 'react-table/react-table.css'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import ReactTable from 'react-table'
import RaisedButton from 'material-ui/RaisedButton'
import { browserHistory } from 'react-router'

class StageDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      stageId: '',
      stageName: this.props.stageName,
      questionsList: this.props.questions,
      selectedQuestion: null
    }
  }

  static propTypes = {
    trialId: PropTypes.any,
    stageName: PropTypes.string,
    questions: PropTypes.array,
    stageId: PropTypes.any,
    getStage: PropTypes.func,
    updateStage: PropTypes.func,
    new: PropTypes.bool,
    addNewStage: PropTypes.func,
    removeStage: PropTypes.func,
    trialName: PropTypes.string,
    getTrialDetail: PropTypes.func
  };
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  viewQuestion () {
    if (this.state.selectedQuestion) {
      let trialId = this.props.trialId
      let stageId = this.props.stageId
      let questionId = this.state.selectedQuestion.id
      browserHistory.push(
        `/trial-manager/trial-detail/${trialId}/stage/${stageId}/question/${questionId}`
      )
    }
  }
  newQuestion () {
    browserHistory.push(
      `/trial-manager/trial-detail/${this.props.trialId}/stage/${this.state.stageId}/new-question`
    )
  }

  componentWillReceiveProps (nextProps) {
    this.setState({
      stageName: nextProps.stageName,
      questionsList: nextProps.questions,
      stageId: nextProps.stageId
    })
  }

  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getStage) {
      this.props.getStage(parseInt(this.props.stageId))
    }
  }

  render () {
    const columns = [
      {
        Header: 'Question Set',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
            width: 100
          },
          {
            Header: 'Name',
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
      <div className='main-container'>
        <div className='pages-box'>
          <div className='stage-container'>
            <div className='stage__header'>
              <h1 className='header__text'>Stage</h1>
              <a
                className='header__link'
                href={`/trial-manager/trial-detail/${this.props.trialId}`}
              >
                {this.props.trialName}
              </a>
            </div>
            <div className='stageDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={this.state.stageId}
                  floatingLabelText='Id'
                  fullWidth
                  underlineShow={false}
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'stageName')}
                  value={this.state.stageName}
                  floatingLabelText='Name'
                  fullWidth
                />
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  new={this.props.new}
                  trialId={this.props.trialId}
                  stageName={this.state.stageName}
                  stageId={this.state.stageId}
                  updateStage={this.props.updateStage}
                  addNewStage={this.props.addNewStage}
                />
                {!this.props.new && (
                  <RemoveBtn
                    questionsList={this.state.questionsList}
                    removeStage={this.props.removeStage}
                    stageId={this.props.stageId}
                    trialId={this.props.trialId}
                  />
                )}
              </div>
            </div>
            <div className='table__wrapper'>
              <ReactTable
                data={this.state.questionsList}
                columns={columns}
                multiSort
                showPagination={false}
                defaultPageSize={500}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          selectedQuestion: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewQuestion()
                      },
                      style: {
                        background: this.state.selectedQuestion
                          ? rowInfo.original.id ===
                            this.state.selectedQuestion.id
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
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor='#244C7B'
                    labelColor='#FCB636'
                    label='+ New'
                    type='Button'
                    onClick={this.newQuestion.bind(this)}
                  />
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor={
                      this.state.selectedQuestion ? '#FCB636' : '#ccc'
                    }
                    labelColor='#fff'
                    label='Edit'
                    type='Button'
                    disabled={!this.state.selectedQuestion}
                    onClick={this.viewQuestion.bind(this)}
                  />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default StageDetail
