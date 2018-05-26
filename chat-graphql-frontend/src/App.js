import React from 'react'

import { QueryRenderer, graphql } from 'react-relay';

import environment from './createRelayEnvironment'
import RoomSelector from './Pages/RoomSelector'
import Room from './Pages/Room'
import {Route} from 'react-router-dom'

/*export default () => {
  const query = graphql`
    query AppQuery {
      allLinks {
        url
      }
    }
  `
  return <QueryRenderer environment={environment} query={query} render={App} />
}*/

export default () => (
  <div>
    1234
    <Route exact path="/" component={RoomSelector} />
    <Route exact path="/room/:number" component={Room} />
  </div>
)
