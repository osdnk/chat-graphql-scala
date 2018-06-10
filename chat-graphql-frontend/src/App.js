import React from 'react'

import {QueryRenderer, graphql} from 'react-relay'

import environment from './createRelayEnvironment'

export default () => {
  const query = graphql`
    query AppQuery {
      rooms(first: 1) {
          edges {
              node {
                  title
              }
          }
      }
    }
  `
  const variables = {}
  return <QueryRenderer environment={environment} query={query} variables={variables} render={App} />
}

const App = ({error, props}) => {
  if (error) {
    return <div>{error.message}</div>
  } else if (props) {
    console.log(props)
    return <div className="sample">asd</div>
  } else {
    return <div>Loading</div>
  }
}
