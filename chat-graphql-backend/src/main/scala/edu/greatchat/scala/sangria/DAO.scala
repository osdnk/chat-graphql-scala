package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.DBSchema.Links
import edu.greatchat.scala.sangria.DBSchema.Rooms
import edu.greatchat.scala.sangria.DBSchema.Messages
import edu.greatchat.scala.sangria.models._
import sangria.relay._
import slick.jdbc.H2Profile.api._
import models.Repository.roomz
import models.Repository.messagez
import scala.concurrent.Future

class DAO(db: Database) {
  def allLinks = db.run(Links.result)

  def allRooms = db.run(Rooms.result)

  def getLink(id: String): Future[Option[Link]] = db.run(
    Links.filter(_.id === id).result.headOption
  )
  def getLinks(ids: Seq[String]) = db.run(
    Links.filter(_.id inSet ids).result
  )

  def getMessagesByRoomId(id: String): Future[Seq[Message]] = {
    db.run {
      Messages.filter(_.id === id).result
    }
  }

  def roomConnection(connectionArgs: ConnectionArgs): Connection[Room] =
    Connection.connectionFromSeq(roomz, connectionArgs)

  def messageConnection(connectionArgs: ConnectionArgs): Connection[Message] = {
    println(connectionArgs)
    Connection.connectionFromSeq(messagez, connectionArgs)
  }

}
