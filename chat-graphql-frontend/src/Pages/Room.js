import React, {Component} from 'react'

class Room extends Component {
  sendMessage() {

  }
  render() {
    return <div>I'm in room {this.props.match.params.number} </div>
    /*
      render list of messages
     */
  }
}

export default Room
