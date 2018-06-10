import React from 'react'

import {QueryRenderer, graphql} from 'react-relay'

import environment from './createRelayEnvironment'

class Room extends React.Component {
  render() {
    console.log(this.props)

    return <div />
  }
}

export default () => {
  const query = graphql`
    query RoomQuery {
      messagesByRoom(roomId: "1", first: 100) {
        edges {
          node {
            content
          }
        }
      }
    }
  `
  const variables = {}
  return (
    <QueryRenderer
      environment={environment}
      query={query}
      variables={variables}
      render={p => React.createElement(Room, p.props)}
    />
  )
}
