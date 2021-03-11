package co.s4ncampus.fpwithscala.users.domain

import cats.data.{EitherT, OptionT}

trait UserRepositoryAlgebra[F[_]] {
  def create(user: User): F[User]

  def findByLegalId(legalId: String): OptionT[F, User]

  def deleteByLegalId(legalId: String): F[Int]

  //def updatePhoneByLegalId(user: User): F[User]

  //def updateEmailByLegalId(legalId: String, email:String): OptionT[F, User]
  //def updateNameByLegalId(legalId: String, name:String): OptionT[F, User]
  //def updateLastNameByLegalId(legalId: String, lastName:String): OptionT[F, User]

  //def deleteByLegalId(legalId: String): EitherT[F,UserDeleteFailed,Unit]


}