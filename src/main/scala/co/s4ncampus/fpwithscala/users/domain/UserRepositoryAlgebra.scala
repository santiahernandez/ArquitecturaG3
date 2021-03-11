package co.s4ncampus.fpwithscala.users.domain

import cats.data.{EitherT, OptionT}

trait UserRepositoryAlgebra[F[_]] {
  def create(user: User): F[User]

  def findByLegalId(legalId: String): OptionT[F, User]

  def deleteByLegalId(legalId: String): F[Int]

  def updatePhoneByLegalId(legalId: String, phone: String): F[Int]

  def updateEmailByLegalId(legalId: String, email: String): F[Int]

  def updateNameByLegalId(legalId: String, name: String): F[Int]

  def updateLastNameByLegalId(legalId: String, lastName: String): F[Int]

}