package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.models.{Message, _}
import sangria.execution.deferred.Relation
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object DBSchema {

  /**
    * Load schema and populate sample data withing this Sequence od DBActions
    */

  // code below defines mapping model  to database scheme
  class RoomsTable(tag: Tag) extends Table[Room](tag, "ROOMS"){
    def id  = column[String]("ID", O.PrimaryKey, O.AutoInc)
    def title = column[String]("TITLE")
    def description = column[String]("DESCRIPTION")
    def * = (id, title, description).mapTo[Room]
  }
  val Rooms = TableQuery[RoomsTable]

  class MessagesTable(tag: Tag) extends Table[Message](tag, _tableName = "MESSAGES"){
    def id = column[String]("ID", O.PrimaryKey, O.AutoInc)
    def roomId = column[String]("ROOM_ID")
    def content = column[String]("CONTENT")
    def * = (id, roomId, content).mapTo[Message]
    def roomFK = foreignKey("ROOM_ID_FK", roomId, Rooms)(_.id)

  }
  val Messages = TableQuery[MessagesTable]

  val MessagesByRoomId = Relation[Message, String]("byRoom", l => Seq(l.roomId))

  class LinksTable(tag: Tag) extends Table[Link](tag, "LINKS"){
    def id = column[String]("ID", O.PrimaryKey, O.AutoInc)
    def url = column[String]("URL")
    def description = column[String]("DESCRIPTION")
    def * = (id, url, description).mapTo[Link]
  }
  val Links = TableQuery[LinksTable]

  val databaseSetup = DBIO.seq(
    Rooms.schema.create,
    Rooms forceInsertAll Seq(
      Room("1", "General", "General room for all users"),
      Room("2", "Scala", "Let's talk about Scala"),
      Room("3", "Sangria", "Camila Cabello - 'Sangria wine'")
    ),

    Messages.schema.create,
    Messages forceInsertAll Seq(
      Message("1", "1", "veni"),
      Message("2", "1", "vidi"),
      Message("3", "2", "vici"),
      Message("4", "2", "veni"),
      Message("5", "3", "vidi"),
      Message("6", "3", "vici"),
      Message("7", "3", "per"),
      Message("8", "3", "aspera"),
      Message("9", "3", "at"),
    )
  )


  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)

    new DAO(db)

  }

}


