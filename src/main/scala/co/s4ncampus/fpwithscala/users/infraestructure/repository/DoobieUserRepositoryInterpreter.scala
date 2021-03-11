package co.s4ncampus.fpwithscala.users.infraestructure.repository

import co.s4ncampus.fpwithscala.users.domain._

import cats.data._
import cats.syntax.all._
import doobie._
import doobie.implicits._
import cats.effect.Bracket

private object UserSQL {

  def insert(user: User): Update0 = sql"""
    INSERT INTO USERS (LEGAL_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE)
    VALUES (${user.legalId}, ${user.firstName}, ${user.lastName}, ${user.email}, ${user.phone})
  """.update

  def selectByLegalId(legalId: String): Query0[User] = sql"""
    SELECT ID, LEGAL_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE
    FROM USERS
    WHERE LEGAL_ID = $legalId
  """.query[User]

  /**
   *
   * @param legalId
   * @return
   */

  def removeByLegalId(legalId: String): Update0 = sql"""
    DELETE
    FROM USERS
    WHERE LEGAL_ID = $legalId
  """.update

  def putPhoneByLegalId(legalId: String, phone:String): Update0 = sql"""
    UPDATE USERS SET PHONE = ${phone}
    WHERE LEGAL_ID = ${legalId}
  """.update

  def putEmailByLegalId(legalId: String, email:String): Update0 = sql"""
    UPDATE USERS SET EMAIL = ${email}
    WHERE LEGAL_ID = ${legalId}
  """.update

  def putNameByLegalId(legalId: String, name:String): Update0 = sql"""
    UPDATE USERS SET FIRST_NAME = ${name}
    WHERE LEGAL_ID = ${legalId}
  """.update

  def putlastNameByLegalId(legalId: String, lastName:String): Update0 = sql"""
    UPDATE USERS SET LAST_NAME = ${lastName}
    WHERE LEGAL_ID = ${legalId}
  """.update


  //todo: Creacion de funciones update, delete y read en formato SQL
}

class DoobieUserRepositoryInterpreter[F[_]: Bracket[?[_], Throwable]](val xa: Transactor[F])
  extends UserRepositoryAlgebra[F] {

  import UserSQL._

  def create(user: User): F[User] =
    insert(user).withUniqueGeneratedKeys[Long]("ID").map(id => user.copy(id = id.some)).transact(xa)

  def findByLegalId(legalId: String): OptionT[F, User] =
    OptionT(selectByLegalId(legalId).option.transact(xa))

  def deleteByLegalId(legalId: String): F[Boolean] = removeByLegalId(legalId).run.transact(xa).map(l => if (l == 1) true else false)

  def updatePhoneByLegalId(legalId: String, phone:String): F[Boolean] = putPhoneByLegalId(legalId,phone).run.transact(xa).map(l => if (l == 1) true else false)

  def updateEmailByLegalId(legalId: String, email:String): F[Boolean] = putEmailByLegalId(legalId,email).run.transact(xa).map(l => if (l == 1) true else false)

  def updateNameByLegalId(legalId: String, name:String): F[Boolean] = putNameByLegalId(legalId,name).run.transact(xa).map(l => if (l == 1) true else false)

  def updateLastNameByLegalId(legalId: String, lastName:String): F[Boolean] = putlastNameByLegalId(legalId,lastName).run.transact(xa).map(l => if (l == 1) true else false)


  /*def deleteByLegalId(legalId: String): EitherT[F, UserDeleteFailed, Unit] =
    EitherT(removeByLegalId(legalId).run.transact(xa)
      .attempt
      .map(_.leftMap(_ => UserDeleteFailed(legalId)).void))
*/

  //todo: Rspecificar definiciones update, delete y read.
}

/* No tocar plox*/
object DoobieUserRepositoryInterpreter {
  def apply[F[_]: Bracket[?[_], Throwable]](xa: Transactor[F]): DoobieUserRepositoryInterpreter[F] =
    new DoobieUserRepositoryInterpreter[F](xa) {
    }
}