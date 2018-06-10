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
    def author = column[String]("AUTHOR")
    def * = (id, roomId, content, author).mapTo[Message]
    def roomFK = foreignKey("ROOM_ID_FK", roomId, Rooms)(_.id)

  }
  val Messages = TableQuery[MessagesTable]

  val MessagesByRoomId = Relation[Message, String]("byRoom", l => Seq(l.roomId))

  val databaseSetup = DBIO.seq(
    Rooms.schema.create,
    Rooms forceInsertAll Seq(
      Room("1", "General", "General room for all users"),
      Room("2", "Scala", "Let's talk about Scala"),
      Room("3", "Sangria", "Camila Cabello - 'Sangria wine'")
    ),

    Messages.schema.create,
    Messages forceInsertAll Seq(
      Message("1", "1", "veni", "Billie Jean"),
      Message("2", "1", "vidi", "Jack Daniel's"),
      Message("3", "2", "vici", "Cyceron"),
      Message("4", "2", "veni", "Me"),
      Message("5", "3", "vidi", "He"),
      Message("6", "3", "vici", "She"),
      Message("7", "3", "per", "It"),
      Message("8", "3", "aspera", "Mezo"),
      Message("9", "3", "at", "Liber"),
    )
  )


  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)

    new DAO(db)

  }

}


