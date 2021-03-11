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

  def putPhoneByLegalId(legalId:String, phone:String): Update0 = sql"""
    UPDATE USERS SET PHONE = $phone
    WHERE LEGAL_ID = $legalId
  """.update
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

  def putPhoneByLegalId(user:User): Update0 = sql"""
    UPDATE USERS SET PHONE = ${user.phone}
    WHERE LEGAL_ID = ${user.legalId}
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

  def updatePhoneByLegalId(legalId: String, phone:String): F[Int] = putPhoneByLegalId(legalId,phone ).run.transact(xa)

  def deleteByLegalId(legalId: String): F[Int] = removeByLegalId(legalId).run.transact(xa)


  /*def deleteByLegalId(legalId: String): EitherT[F, UserDeleteFailed, Unit] =
    EitherT(removeByLegalId(legalId).run.transact(xa)
      .attempt
      .map(_.leftMap(_ => UserDeleteFailed(legalId)).void))
*/
    //def updatePhoneByLegalId(user: User): F[User]=  putPhoneByLegalId(user).run.transact(xa).as(user)


  //todo: Rspecificar definiciones update, delete y read.
}

/* No tocar plox*/
object DoobieUserRepositoryInterpreter {
  def apply[F[_]: Bracket[?[_], Throwable]](xa: Transactor[F]): DoobieUserRepositoryInterpreter[F] =
    new DoobieUserRepositoryInterpreter[F](xa) {
    }
}