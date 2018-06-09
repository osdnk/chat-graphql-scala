import {createPaginationContainer, graphql} from 'react-relay'
import { Component } from 'react'

export default class RoomSelector extends Component {
  render() {
    return (
      <div>
        {this.props.user.feed.edges.map(edge => console.log(edge))}
        <button onPress={() => this._loadMore()} title="Load More" />
      </div>
    )
  }

  _loadMore() {
    if (!this.props.relay.hasMore() || this.props.relay.isLoading()) {
      return
    }

    this.props.relay.loadMore(
      10, // Fetch the next 10 feed items
      error => {
        console.log(error)
      }
    )
  }
}
/*

export  g  () => createPaginationContainer(
  RoomSelector,
  {
    user: graphql`
        fragment RoomSelector_user on Query
        @argumentDefinitions(count: {type: "Int", defaultValue: 10}, cursor: {type: "String"}) {
            rooms(first: $count, after: $cursor) @connection(key: "RoomSelector_rooms") {
                edges {
                    node {
                        id
                    }
                }
            }
        }
    `
  },
  {
    direction: 'forward',
    getConnectionFromProps(props) {
      return props.user && props.user.feed
    },
    // This is also the default implementation of `getFragmentVariables` if it isn't provided.
    getFragmentVariables(prevVars, totalCount) {
      return {
        ...prevVars,
        count: totalCount
      }
    },
    getVariables(props, {count, cursor}, fragmentVariables) {
      return {
        count,
        cursor
      }
    },
    query: graphql`
        # Pagination query to be fetched upon calli
        # Notice that we re-use our fragment, and the shape of this query matches our fragment spec.
        query RoomSelectorQuery($count: Int!, $cursor: String) {
            ...RoomSelector_user @arguments(count: $count, cursor: $cursor)
        }
    `
  }
)
*/
