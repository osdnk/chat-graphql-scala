package edu.greatchat.scala.sangria

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import sangria.ast.Document
import sangria.execution._
import sangria.marshalling.sprayJson._
import sangria.parser.QueryParser
import spray.json.{JsObject, JsString, JsValue}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


object GraphQLServer {

  // We need access to the database, so it’s the step where such connection is created.
  private val dao = DBSchema.createDatabase

  // It will be used directly in the routing of HTTP server.
  def endpoint(requestJSON: JsValue)(implicit ec: ExecutionContext): Route = {

    /*
    Main JSON Object is extracted from the root object. From this object will be extracted three children. The format of root JSON object expected by GraphQL implementation looks like this, and every from those 3 keys will be extracted and passed for execution.

      {
        query: {},
        variables: {},
        operationName: ""
      }
    */
    val JsObject(fields) = requestJSON

    val JsString(query) = fields("query")

    // 5
    QueryParser.parse(query) match {
      case Success(queryAst) =>
        val operation = fields.get("operationName") collect {
          case JsString(op) => op
        }
        val variables = fields.get("variables") match {
          case Some(obj: JsObject) => obj
          case _ => JsObject.empty
        }
        complete(executeGraphQLQuery(queryAst, operation, variables))
      case Failure(error) =>
        complete(BadRequest, JsObject("error" -> JsString(error.getMessage)))
    }

  }

  private def executeGraphQLQuery(query: Document, operation: Option[String], vars: JsObject)(implicit ec: ExecutionContext) = {
    // 9
    Executor.execute(
      GraphQLSchema.SchemaDefinition, // 10
      query, // 11
      Context(dao), // 12
      variables = vars, // 13
      operationName = operation // 14
    ).map(OK -> _)
      .recover {
        case error: QueryAnalysisError => BadRequest -> error.resolveError
        case error: ErrorWithResolver => InternalServerError -> error.resolveError
      }
  }

}