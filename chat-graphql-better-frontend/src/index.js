import React from 'react';
import ReactDOM from 'react-dom';
// @flow
import BrowserProtocol from 'farce/lib/BrowserProtocol';
import createFarceRouter from 'found/lib/createFarceRouter';
import createRender from 'found/lib/createRender';
import environment from './createRelayEnvironment';
import queryMiddleware from 'farce/lib/queryMiddleware';
import { Resolver } from 'found-relay';
import routes from './routes';


require('file-loader?name=[name].[ext]!../public/index.html');
require('../public/stylesheets/main.scss');

const Router = createFarceRouter({
  historyMiddlewares: [queryMiddleware],
  historyProtocol: new BrowserProtocol(),
  render: createRender({}),
  routeConfig: routes
});

ReactDOM.render(
  <Router resolver={new Resolver(environment)} />,
  // $FlowFixMe
  document.getElementById('root')
);
