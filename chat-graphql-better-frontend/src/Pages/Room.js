import React, {Component} from 'react'
import { Link } from 'react-router-dom';

class Room extends Component {
  sendMessage() {

  }
  render() {
    return (
      <div>
        <div className="roomer">I'm in room {this.props.match.params.number} </div>
        <Link to="/room/4">123</Link>
      </div>
    )
  }
}

export default Room;
