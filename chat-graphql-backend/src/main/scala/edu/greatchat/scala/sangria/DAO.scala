package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.DBSchema.Rooms
import edu.greatchat.scala.sangria.DBSchema.Messages
import edu.greatchat.scala.sangria.models._
import sangria.relay._
import slick.jdbc.H2Profile.api._
import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global

class DAO(db: Database) {
  def allRooms: Future[Seq[Room]] = db.run(Rooms.result)

  def allMessages: Future[Seq[Message]] = db.run(Messages.result)

  def getMessagesByRoomId(roomId: String): Future[Seq[Message]] = {
    db.run {
      Messages.filter(_.roomId === roomId).result
    }
  }

  def roomConnection(connectionArgs: ConnectionArgs): Future[Connection[Room]] =
    Connection.connectionFromFutureSeq(allRooms, connectionArgs)

  def messageConnectionByRoom(id: String, connectionArgs: ConnectionArgs): Future[Connection[Message]] = {
    Connection.connectionFromFutureSeq(getMessagesByRoomId(id), connectionArgs)
  }

  def createMessage(roomId: String, content: String, author: String): Future[Message] = {
    val insertAndReturnMessageQuery = (Messages returning Messages.map(_.id)) into {
      (message, id) => message.copy(id = id)
    }
    db.run{
      insertAndReturnMessageQuery += Message("", roomId, content, author)
    }
  }

  def createRoom(title: String, description: String): Future[Room] = {
    val insertAndReturnRoomQuery = (Rooms returning Rooms.map(_.id)) into {
      (room, id) => room.copy(id = id)
    }
    db.run{
      insertAndReturnRoomQuery += Room("", title, description)
    }
  }
}
