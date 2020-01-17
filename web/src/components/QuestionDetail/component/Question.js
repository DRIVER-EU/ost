import React, { Component } from 'react'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import ReactTable from 'react-table'
import PropTypes from 'prop-types'
import { browserHistory } from 'react-router'
import RaisedButton from 'material-ui/RaisedButton'
import 'react-table/react-table.css'
import './QuestionDetail.scss'
import SelectField from 'material-ui/SelectField'
import MenuItem from 'material-ui/MenuItem'
import Checkbox from 'material-ui/Checkbox'

class Question extends Component {
  constructor (props) {
    super(props)
    this.state = {
      questionId: '',
      questionName: this.props.questionName,
      description: this.props.description,
      position: this.props.position,
      questionsDetailList: this.props.questionsDetailList,
      selectedQuestionDetail: null,
      withUsers: this.props.withUsers,
      multiplicity: this.props.multiplicity,
      answerType: [
        { value: 'CHECKBOX', text: 'checkbox' },
        { value: 'RADIO_BUTTON', text: 'radio button' },
        { value: 'SLIDER', text: 'slider' },
        { value: 'TEXT_FIELD', text: 'text field' },
        { value: 'BOX_LIST', text: 'box list' },
        { value: 'RADIO_LINE', text: 'radio line' }
      ],
      selectedAnswerType: 'CHECKBOX',
      commented: true,
      required: true
    }
  }
  static propTypes = {
    trialId: PropTypes.any,
    trialName: PropTypes.string,
    stageId: PropTypes.any,
    stageName: PropTypes.string,
    new: PropTypes.bool,
    updateQuestion: PropTypes.func,
    addNewQuestion: PropTypes.func,
    removeQuestion: PropTypes.func,
    getTrialDetail: PropTypes.func,
    getStageById: PropTypes.func,
    questionId: PropTypes.any,
    questionsDetailList: PropTypes.array,
    questionName: PropTypes.string,
    description: PropTypes.string,
    position: PropTypes.any,
    withUsers: PropTypes.bool,
    multiplicity: PropTypes.bool,
    getQuestion: PropTypes.func
  };
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  viewQuestionDetail () {
    if (this.state.selectedQuestionDetail) {
      let trialId = this.props.trialId
      let stageId = this.props.stageId
      let questionId = this.props.questionId
      let questionDetailId = this.state.selectedQuestionDetail.id
      browserHistory.push(
        `/trial-manager/trial-detail/${trialId}/stage/${stageId}/question/${questionId}/question-detail/${questionDetailId}`
      )
    }
  }
  handleChange = (event, index, value) =>
    this.setState({ selectedAnswerType: value });
  updateCheck (name) {
    this.setState({
      [name]: !this.state[name]
    })
  }
  componentWillReceiveProps (nextProps) {
    this.setState({
      questionName: nextProps.questionName,
      questionsDetailList: nextProps.questionsDetailList,
      questionId: nextProps.questionId,
      description: nextProps.description,
      position: nextProps.position,
      withUsers: nextProps.withUsers,
      multiplicity: nextProps.multiplicity
    })
  }

  componentDidMount () {
    if (this.props.getTrialDetail) {
      this.props.getTrialDetail(this.props.trialId)
    }
    if (this.props.getStageById) {
      this.props.getStageById(this.props.stageId)
    }
    if (this.props.getQuestion) {
      this.props.getQuestion(this.props.questionId)
    }
  }
  render () {
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
            accessor: 'value'
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
              <h1 className='header__text'>Question</h1>
              <div>
                <a
                  className='header__link'
                  href={`/trial-manager/trial-detail/${this.props.trialId}`}
                >
                  {this.props.trialName}
                </a>
                <a
                  className='header__link'
                  href={`/trial-manager/trial-detail/${this.props.trialId}/stage/${this.props.stageId}`}
                >
                  {this.props.stageName}
                </a>
              </div>
            </div>
            <div className='stageDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={this.state.questionId}
                  floatingLabelText='Id'
                  fullWidth
                  underlineShow={false}
                />
                <TextField
                  type='name'
                  onChange={this.handleChangeInput.bind(this, 'questionName')}
                  value={this.state.questionName}
                  floatingLabelText='Name'
                  fullWidth
                />
              </div>
              <div className='btns__wrapper'>
                <SaveBtn
                  new={this.props.new}
                  trialId={this.props.trialId}
                  stageId={this.props.stageId}
                  questionName={this.state.questionName}
                  questionId={this.state.questionId}
                  description={this.state.description}
                  position={this.state.position}
                  updateQuestion={this.props.updateQuestion}
                  addNewQuestion={this.props.addNewQuestion}
                  withUsers={this.state.withUsers}
                  multiplicity={this.state.multiplicity}
                />
                {!this.props.new && (
                  <RemoveBtn
                    questionsList={this.state.questionsDetailList}
                    removeQuestion={this.props.removeQuestion}
                    stageId={this.props.stageId}
                    trialId={this.props.trialId}
                    questionId={this.state.questionId}
                  />
                )}
              </div>
            </div>
            <TextField
              type='description'
              onChange={this.handleChangeInput.bind(this, 'description')}
              value={this.state.description}
              floatingLabelText='Description'
              fullWidth
              multiLine
              rowsMax={8}
            />
            <div>
              <SelectField
                floatingLabelText='Answer type'
                value={this.state.selectedAnswerType}
                onChange={this.handleChange}
              >
                <MenuItem
                  value={this.state.answerType[0].value}
                  primaryText={this.state.answerType[0].text}
                />
                <MenuItem
                  value={this.state.answerType[1].value}
                  primaryText={this.state.answerType[1].text}
                />
                <MenuItem
                  value={this.state.answerType[2].value}
                  primaryText={this.state.answerType[2].text}
                />
                <MenuItem
                  value={this.state.answerType[3].value}
                  primaryText={this.state.answerType[3].text}
                />
                <MenuItem
                  value={this.state.answerType[4].value}
                  primaryText={this.state.answerType[4].text}
                />
                <MenuItem
                  value={this.state.answerType[5].value}
                  primaryText={this.state.answerType[5].text}
                />
              </SelectField>
            </div>
            <div className='position__field'>
              <TextField
                type='position'
                onChange={this.handleChangeInput.bind(this, 'position')}
                value={this.state.position}
                floatingLabelText='Question position'
                fullWidth
                />
            </div>
            <Checkbox
              label='Commented'
              checked={this.state.commented}
              onCheck={this.updateCheck.bind(this, 'commented')}
            />
            <Checkbox
              label='Required'
              checked={this.state.required}
              onCheck={this.updateCheck.bind(this, 'required')}
            />
            <div className='table__wrapper'>
              <ReactTable
                data={this.state.questionsDetailList}
                columns={columns}
                multiSort
                showPagination={false}
                minRows={0}
                getTdProps={(state, rowInfo) => {
                  if (rowInfo && rowInfo.row) {
                    return {
                      onClick: e => {
                        this.setState({
                          selectedQuestionDetail: rowInfo.original
                        })
                      },
                      onDoubleClick: e => {
                        this.viewQuestion()
                      },
                      style: {
                        background: this.state.selectedQuestionDetail
                          ? rowInfo.original.id ===
                            this.state.selectedQuestionDetail.id
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
                  />
                  <RaisedButton
                    buttonStyle={{ width: '200px' }}
                    backgroundColor={
                      this.state.selectedQuestionDetail ? '#FCB636' : '#ccc'
                    }
                    labelColor='#fff'
                    label='Edit'
                    type='Button'
                    disabled={!this.state.selectedQuestionDetail}
                    onClick={this.viewQuestionDetail.bind(this)}
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
export default Question
