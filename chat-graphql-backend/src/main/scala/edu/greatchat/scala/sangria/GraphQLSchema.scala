package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.models._
import sangria.relay._
import sangria.schema.{Field, ObjectType}
import sangria.schema._

object GraphQLSchema {
  val MessageType = ObjectType[Context, Message](
    name = "Message",
    fields[Context, Message](
      Field("id", IDType, resolve = _.value.id),
      Field("roomId", StringType, resolve = _.value.roomId),
      Field("content", StringType, resolve = _.value.content)
    )
  )

  val ConnectionDefinition(_, messageConnection) =
    Connection.definition[Context, Connection, Message]("Messages",
      MessageType)


  val RoomType = ObjectType[Context, Room](
    "Room",
    fields[Context, Room](
      Field("id", IDType, resolve = _.value.id),
      Field("title", StringType, resolve = _.value.title),
      Field("description", StringType, resolve = _.value.description),
      Field("messages", OptionType(messageConnection),
        arguments = Connection.Args.All,
        resolve = c => c.ctx.dao.messageConnectionByRoom(c.value.id, ConnectionArgs(c)))
    )
  )

  val ConnectionDefinition(_, roomConnection) =
    Connection.definition[Context, Connection, Room](name = "Rooms", RoomType)

  val QueryType = ObjectType(
    "Query",
    fields[Context, Unit](
      Field("messagesByRoom",
        OptionType(messageConnection),
        arguments = List(Argument("roomId", StringType)) ::: Connection.Args.All,
        resolve = c => c.ctx.dao.messageConnectionByRoom(c.arg[String](name = "roomId"), ConnectionArgs(c))
      ),
      Field("rooms", OptionType(roomConnection),
        arguments = Connection.Args.All,
        resolve = c => c.ctx.dao.roomConnection(ConnectionArgs(c)))
    ))

  val RoomIdArg = Argument("roomId", StringType)
  val ContentArg = Argument("content", StringType)
  val TitleArg = Argument("title", StringType)
  val DescriptionArg = Argument("description", StringType)

  val Mutation = ObjectType(
    "Mutation",
    fields[Context, Unit](
      Field("createMessage",
        OptionType(MessageType),
        arguments = RoomIdArg :: ContentArg :: Nil,
        resolve = c => c.ctx.dao.createMessage(c.arg(RoomIdArg), c.arg(ContentArg))
      ),
      Field("createRoom",
        OptionType(RoomType),
        arguments = TitleArg :: DescriptionArg :: Nil,
        resolve = c => c.ctx.dao.createRoom(c.arg(TitleArg), c.arg(DescriptionArg))
      )
    )
  )

  val SchemaDefinition = Schema(QueryType, Some(Mutation))
}