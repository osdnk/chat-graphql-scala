import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import { Route } from 'react-router-dom';
import RoomSelector from './Pages/RoomSelector';
import Room from './Pages/Room';



const App = () => (
    <div className="content">
      <Route exact path="/" component={RoomSelector} />
      <Route exact path="/room/:number" component={Room} />
    </div>
);

export default App;
