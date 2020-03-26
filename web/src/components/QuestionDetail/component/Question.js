import React, { Component } from 'react'
import TextField from 'material-ui/TextField'
import SaveBtn from './SaveBtn'
import RemoveBtn from './RemoveBtn'
import PropTypes from 'prop-types'
import { browserHistory } from 'react-router'
import 'react-table/react-table.css'
import './QuestionDetail.scss'
import SelectField from 'material-ui/SelectField'
import MenuItem from 'material-ui/MenuItem'
import Checkbox from 'material-ui/Checkbox'
import Option from './Option'

class Question extends Component {
  constructor (props) {
    super(props)
    this.state = {
      questionDetailId: this.props.questionDetailId,
      questionName: this.props.questionName || '',
      description: this.props.description || '',
      position: this.props.position || '',
      option: this.props.option,
      selectedOption: null,
      commented: this.props.commented || false,
      required: false,
      answerType: [
        { value: 'CHECKBOX', text: 'checkbox' },
        { value: 'RADIO_BUTTON', text: 'radio button' },
        { value: 'SLIDER', text: 'slider' },
        { value: 'TEXT_FIELD', text: 'text field' },
        { value: 'RADIO_LINE', text: 'radio line' }
      ],
      selectedAnswerType: this.props.answerType || 'CHECKBOX'
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
    option: PropTypes.array,
    questionName: PropTypes.string,
    description: PropTypes.string,
    position: PropTypes.any,
    commented: PropTypes.bool,
    getQuestion: PropTypes.func,
    answerType: PropTypes.string,
    questionDetailId: PropTypes.any,
    questionSetName: PropTypes.string,
    getQuestionSet: PropTypes.func,
    addOption: PropTypes.func,
    removeOption: PropTypes.func
  };
  handleChangeInput (name, e) {
    let change = {}
    change[name] = e.target.value
    this.setState(change)
  }
  viewOption () {
    if (this.state.selectedQuestionDetail) {
      let trialId = this.props.trialId
      let stageId = this.props.stageId
      let questionId = this.props.questionId
      let questionDetailId = this.props.questionDetailId
      let questionPath = `/trial-manager/trial-detail/${trialId}/stage/${stageId}/question/${questionId}`
      browserHistory.push(
        `${questionPath}/question-detail/${questionDetailId}`
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
      questionDetailId: nextProps.questionDetailId,
      description: nextProps.description,
      position: nextProps.position,
      selectedAnswerType: nextProps.answerType,
      option: nextProps.option
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
      this.props.getQuestion(this.props.questionDetailId)
    }
    if (this.props.getQuestionSet) {
      this.props.getQuestionSet(this.props.questionId)
    }
  }
  render () {
    let trialId = this.props.trialId
    let stageId = this.props.stageId
    let questionId = this.props.questionId
    let questionPath = `/trial-manager/trial-detail/${trialId}/stage/${stageId}/question/${questionId}`
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
                <a
                  className='header__link'
                  href={questionPath}
                >
                  {this.props.questionSetName}
                </a>
              </div>
            </div>
            <div className='stageDetail__info'>
              <div className='info__container'>
                <TextField
                  type='id'
                  value={this.props.questionDetailId}
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
                  questionDetailId={this.props.questionDetailId}
                  description={this.state.description}
                  position={this.state.position}
                  updateQuestion={this.props.updateQuestion}
                  addNewQuestion={this.props.addNewQuestion}
                  commented={this.state.commented}
                  required={this.state.required}
                  answerType={this.state.selectedAnswerType}
                  questionId={this.props.questionId}
                  inputsValue={[this.state.questionName, this.state.description]}
                  getQuestion={this.props.getQuestion}
                />
                {!this.props.new && (
                  <RemoveBtn
                    option={this.state.option || []}
                    removeQuestion={this.props.removeQuestion}
                    stageId={this.props.stageId}
                    trialId={this.props.trialId}
                    questionId={this.props.questionId}
                    questionDetailId={this.props.questionDetailId}
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
                disabled={this.state.questionDetailId ? parseInt(this.state.questionDetailId) > 0 : false}
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
            <Option
              option={this.state.option}
              new={this.props.new}
              questionDetailId={this.props.questionDetailId}
              addOption={this.props.addOption}
              getQuestion={this.props.getQuestion}
              removeOption={this.props.removeOption}
            />
          </div>
        </div>
      </div>
    )
  }
}
export default Question
