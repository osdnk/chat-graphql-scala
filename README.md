# Chat
Simple webchat written in Scala and JavaScript. Uses Sangria - Scala GraphQL Implementation.

## Backend
As long as you've installed the latest version of `sbt` (in order to manage scala packages and run server locally) simple run:

In `/chat-graphql-backend`
```bash
sbt run
```
Everything should be done automatically and then your 8080 port graphiql dev console should appear.


## Frontend
### Prerequirements and installation
As long as you've installed the latest versions of `node`,  `yarn` (or `npm`; for managing js packages and run frontend app), simple run:
```bash
npm i -g graphql
```
or:
```bash
yarn global add graphql
```
in order to install GraphQL globally and then in `/chat-graphql-frontend`:
```bash
yarn i
```
or:
```bash
npm i
```
in order to install dependencies.

### Run
Script `start` defined in package.json will run `graphql get-schema && webpack-dev-server`.  
`graphql get-schema` will download schema.graphql from server. This file gives us data representation.  
`webpack-dev-server` will rebuild the application when the code changes
```bash
yarn start
```
or:
```bash
npm run start
```

Your frontend app should be hosted on 8081 port.










