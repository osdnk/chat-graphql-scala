import React from 'react'

import {QueryRenderer, graphql} from 'react-relay'

import environment from './createRelayEnvironment'
import RoomSelector from './RoomSelector'
import Room from './Room'

export default () => {
  const query = graphql`
    query AppQuery {
      rooms(first: 100) {
        edges {
          node {
            id
            title
            messages(last: 1) {
              edges {
                node {
                  content
                }
              }
            }
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
      render={p => React.createElement(App, p.props)}
    />
  )
}

class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      selectedRoom: -1
    }
  }

  render() {
    if (!this.props.rooms) {
      return <div>Loading</div>
    }
    return (
      <div>
        Select room
        <button onClick={() => this.setState({selectedRoom: -1})}>back home</button>
        {this.state.selectedRoom === -1 && (
          <RoomSelector rooms={this.props.rooms} changeRoom={r => this.setState({selectedRoom: r})} />
        )}
        {this.state.selectedRoom !== -1 && (
          <div>
            We're in the room {this.state.selectedRoom}
            <Room roomId={this.state.selectedRoom} />
          </div>
        )}
      </div>
    )
  }
}
