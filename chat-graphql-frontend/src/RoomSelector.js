import React from 'react'

import {QueryRenderer, graphql} from 'react-relay'

import environment from './createRelayEnvironment'


export default class RoomSelector extends React.Component {
  render() {
    console.log(this.props)
    return (
      <div>
        {this.props.rooms.edges.map(e =>
          <button
            onClick = {() => this.props.changeRoom(e.node.id)}
          >
            {e.node.title}
          </button>
        )}
      </div>
    )
  }
}
