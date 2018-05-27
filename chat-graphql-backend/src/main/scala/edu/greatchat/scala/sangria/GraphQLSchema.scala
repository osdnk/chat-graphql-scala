package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.models._
import edu.greatchat.scala.sangria.DBSchema._
import sangria.relay._
import sangria.schema.{Field, ListType, ObjectType}
import sangria.schema._

object GraphQLSchema {

  // 1
  val LinkType = ObjectType[Unit, Link](
    "Link",
    fields[Unit, Link](
      Field("id", IDType, resolve = _.value.id),
      Field("url", StringType, resolve = _.value.url),
      Field("description", StringType, resolve = _.value.description)
    )
  )
  val MessageType = ObjectType[Unit, Message](
    name = "Message",
    fields[Unit, Message](
      Field("id", IDType, resolve = _.value.id),
      Field("roomId", StringType, resolve = _.value.roomId),
      Field("content", StringType, resolve = _.value.content)
    )
  )

  val ConnectionDefinition(_, messageConnection) =
    Connection.definition[Context, Connection, Message]("Messages",
      MessageType)

  val RoomType = ObjectType[Unit, Room](
    "Room",
    fields[Unit, Room](
      Field("id", IDType, resolve = _.value.id),
      Field("title", StringType, resolve = _.value.title)))



  // 2
  val QueryType = ObjectType(
    "Query",
    fields[Context, Unit](
      Field("allLinks", ListType(LinkType), resolve = c => c.ctx.dao.allLinks),
      Field("allRooms", ListType(RoomType), resolve = c => c.ctx.dao.allRooms),
      Field("link",
        OptionType(LinkType),
        arguments = List(Argument("id", StringType)),
        resolve = c => c.ctx.dao.getLink(c.arg[String]("id"))
      ),
      Field("links",
        ListType(LinkType),
        arguments = List(Argument("ids", ListInputType(StringType))),
        resolve = c => c.ctx.dao.getLinks(c.arg[Seq[String]]("ids"))
      ),
      Field("messagesByRoom",
        ListType(MessageType),
        arguments = List(Argument("roomId", StringType)),
        resolve = c => c.ctx.dao.getMessagesByRoomId(c.arg[String](name="roomId"))
      ),
      Field("messages", OptionType(messageConnection),
        arguments = Connection.Args.All,
        resolve = c => c.ctx.dao.messageConnection(ConnectionArgs(c)))))

  // 3
  val SchemaDefinition = Schema(QueryType)
}