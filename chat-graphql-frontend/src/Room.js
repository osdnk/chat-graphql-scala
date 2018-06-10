import React from 'react'

import {QueryRenderer, graphql} from 'react-relay'

import environment from './createRelayEnvironment'

class Room extends React.Component {
  render() {
    if (!this.props.messagesByRoom)
      return <div/>
    return (<div>
      {this.props.messagesByRoom.edges.map(e =>
        <div>
          {e.node.content}
        </div>

      )}
    </div>)
  }
}

export default ({ roomId }) => {
  const query = graphql`
    query RoomQuery($roomId: String!) {
      messagesByRoom(roomId: $roomId, first: 100) {
        edges {
          node {
            content
          }
        }
      }
    }
  `
  return (
    <QueryRenderer
      environment={environment}
      query={query}
      variables={{
        roomId
      }}
      render={p => React.createElement(Room, p.props)}
    />
  )
}
