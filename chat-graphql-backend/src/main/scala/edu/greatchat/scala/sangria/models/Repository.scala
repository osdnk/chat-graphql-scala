package edu.greatchat.scala.sangria.models

import edu.greatchat.scala.sangria.DBSchema.{Messages, Rooms}
import sangria.relay.{Connection, ConnectionArgs}

/*
class Repository {

  import Repository._
  def roomConnection(connectionArgs: ConnectionArgs): Connection[Room] =
    Connection.connectionFromSeq(roomz, connectionArgs)
/*
  def messageConnection(RoomId: String, connectionArgs: ConnectionArgs): Connection[Message] =
    Connection.connectionFromSeq(messages.filter(_.roomId == RoomId), connectionArgs)
}*/

*/

object Repository{
  val roomz = Seq(
    Room("1", "General", "General room for all users"),
    Room("2", "Scala", "Let's talk about Scala"),
    Room("3", "Sangria", "Camila Cabello - 'Sangria wine'")
  )

  val messagez = Seq(
    Message("1", "1", "Hi everyone in General Room"),
    Message("2", "3", "Who thinks Camilla Cabello is hot?"),
    Message("3", "3", "Oh yeah, she's definitely hot"),
    Message("4", "1", "veni"),
    Message("5", "3", "vidi"),
    Message("6", "3", "vici"),
    Message("7", "3", "per"),
    Message("8", "3", "aspera"),
    Message("9", "3", "at"),
    Message("9", "3", "astra"),
  )
}