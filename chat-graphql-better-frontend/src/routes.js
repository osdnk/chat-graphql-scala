// @flow
import makeRouteConfig from 'found/lib/makeRouteConfig';
import Route from 'found/lib/Route'; // eslint-disable-line
import React from 'react';
import { graphql } from 'react-relay';

import Room from './Pages/Room';
import RoomSelector from './Pages/RoomSelector';

/*
  Relay static queries. They must be defined *before* the export located below
  and they cannot use template literals (making developers sad).
*/
/*
const queries = {
  Container: graphql`
    query routes_Container_Query {
      viewer {
        ...Container_viewer
      }
    }
  `,
  HomePage: graphql`
    query routes_HomePage_Query {
      viewer {
        ...HomePage_viewer
      }
    }
  `,
  ScheduleWrapperPage: graphql`
    query routes_ScheduleWrapperPage_Query {
      viewer {
        ...ScheduleWrapperPage_viewer
      }
    }
  `,
  ImpossibilitiesPage: graphql`
    query routes_ImpossibilitiesPage_Query {
      viewer {
        ...ImpossibilitiesPage_viewer
      }
    }
  `,
  NewEnrollmentPage: graphql`
    query routes_NewEnrollmentPage_Query {
      viewer {
        ...NewEnrollmentPage_viewer
      }
    }
  `,
  EnrollmentsPage: graphql`
    query routes_EnrollmentsPage_Query {
      viewer {
        ...EnrollmentsPage_viewer
      }
    }
  `,
  UsersPage: graphql`
    query routes_UsersPage_Query {
      viewer {
        ...UsersPage_viewer
      }
    }
  `,
  ParticipantsPage: graphql`
    query routes_ParticipantsPage_Query($slug: String!) {
      viewer {
        ...ParticipantsPage_viewer
      }
    }
  `,
  AdminSubjectsPage: graphql`
    query routes_AdminSubjectsPage_Query($slug: String!) {
      viewer {
        ...AdminSubjectsPage_viewer
      }
    }
  `,
  AdminImpossibilitiesPage: graphql`
    query routes_AdminImpossibilitiesPage_Query($slug: String!) {
      viewer {
        ...AdminImpossibilitiesPage_viewer
      }
    }
  `,
  AdminSchedulePage: graphql`
    query routes_AdminSchedulePage_Query($slug: String!) {
      viewer {
        ...AdminSchedulePage_viewer
      }
    }
  `,
  TermsPage: graphql`
    query routes_TermsPage_Query($slug: String!) {
      viewer {
        ...TermsPage_viewer
      }
    }
  `,
  ImportPage: graphql`
    query routes_ImportPage_Query($slug: String!) {
      viewer {
        ...ImportPage_viewer
      }
    }
  `
};
*/

export default makeRouteConfig(
  <Route path="/">
    <Route exact path="/" Component={RoomSelector} />
    <Route
      path="room/:number"
      Component={Room}
    />
  </Route>
);
