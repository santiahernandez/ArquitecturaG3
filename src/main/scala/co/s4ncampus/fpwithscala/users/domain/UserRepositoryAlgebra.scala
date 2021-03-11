package co.s4ncampus.fpwithscala.users.domain

import cats.data.{EitherT, OptionT}

trait UserRepositoryAlgebra[F[_]] {
  def create(user: User): F[User]

  def findByLegalId(legalId: String): OptionT[F, User]

  def deleteByLegalId(legalId: String): F[Boolean]

  def updatePhoneByLegalId(legalId: String, phone: String): F[Boolean]

  def updateEmailByLegalId(legalId: String, email: String): F[Boolean]

  def updateNameByLegalId(legalId: String, name: String): F[Boolean]

  def updateLastNameByLegalId(legalId: String, lastName: String): F[Boolean]

}