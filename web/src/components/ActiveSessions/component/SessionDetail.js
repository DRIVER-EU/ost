import PropTypes from 'prop-types'
import React, { Component } from 'react'
import RaisedButton from 'material-ui/RaisedButton'
import ReactTable from 'react-table'

class SessionDetail extends Component {
  constructor (props) {
    super(props)
    this.state = {
      sessionDetail: this.props.sessionDetail
    }
  }
  static propTypes = {
    id: PropTypes.any,
    getSessionDetail: PropTypes.func,
    sessionDetail: PropTypes.array
  };
  componentDidMount () {
    this.updateTable()
  }
  componentDidUpdate (prevProps) {
    if (this.props.id !== prevProps.id) {
      this.updateTable()
    }
  }
  updateTable = () => {
    if (this.props.getSessionDetail) {
      this.props.getSessionDetail(this.props.id)
    }
  }
  componentWillReceiveProps (nextProps) {
    this.setState({
      sessionDetail: nextProps.sessionDetail
    })
  }
  render () {
    const columns = [
      {
        Header: 'Id',
        accessor: 'id',
        width: 100,
        style: { textAlign: 'right' }
      },
      {
        Header: 'Login',
        width: 200,
        accessor: 'login',
        style: { textAlign: 'left' }
      },
      {
        Header: 'Role',
        accessor: 'trialRoleName',
        width: 200,
        style: { textAlign: 'left' }
      },
      {
        Header: 'Account',
        columns: [
          {
            Header: 'Questions',
            accessor: 'activeSession.stageStatistics[0].numberOfQuestions',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Answers',
            accessor: 'activeSession.stageStatistics[0].totalNumberOfAnswers',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Missing questions',
            accessor: 'activeSession.stageStatistics[0].numberOfQuestionsWithOutAnswer',
            style: { textAlign: 'left' }
          },
          {
            Header: 'Active',
            width: 150,
            Cell: props => <RaisedButton
              backgroundColor='#FCB636'
              labelColor='#fff'
              label='Send Message'
              type='Button' />
          }
        ]
      }
    ]
    return (
      <div className='table__wrapper'>
        <div className='table__header'>
          <h2 className='header__text'>User List</h2>
          <RaisedButton
            backgroundColor='#FCB636'
            labelColor='#fff'
            label='Send Message'
            type='Button'
                      />
        </div>
        <ReactTable
          data={this.state.sessionDetail}
          columns={columns}
          multiSort
          showPagination={false}
          defaultPageSize={500}
          minRows={0}

        />
      </div>
    )
  }
}
export default SessionDetail
