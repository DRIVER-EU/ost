import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Router } from 'react-router'
import { Provider } from 'react-redux'
import createHashHistory from 'history/lib/createHashHistory'
import ReduxToastr from 'react-redux-toastr'

class AppContainer extends Component {
  static propTypes = {
    routes : PropTypes.object.isRequired,
    store  : PropTypes.object.isRequired
  }

  render () {
    const { routes, store } = this.props
    let history = createHashHistory({

    })
    return (
      <Provider store={store}>
        <div style={{ height: '100%' }}>
          <Router history={history} children={routes} />
          <ReduxToastr
            timeOut={4000}
            newestOnTop={false}
            preventDuplicates
            position='top-left'
            transitionIn='fadeIn'
            transitionOut='fadeOut'
            progressBar />
        </div>
      </Provider>
    )
  }
}

export default AppContainer
