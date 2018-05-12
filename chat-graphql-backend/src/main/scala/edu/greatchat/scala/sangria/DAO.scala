package edu.greatchat.scala.sangria

import edu.greatchat.scala.sangria.DBSchema.Links
import slick.jdbc.H2Profile.api._

class DAO(db: Database) {
  def allLinks = db.run(Links.result)
}
