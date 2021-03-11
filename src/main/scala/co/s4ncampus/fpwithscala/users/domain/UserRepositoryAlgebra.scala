package co.s4ncampus.fpwithscala.users.domain

import cats.data.{EitherT, OptionT}

trait UserRepositoryAlgebra[F[_]] {
  def create(user: User): F[User]

  def findByLegalId(legalId: String): OptionT[F, User]

  def deleteByLegalId(user: User): F[Boolean]

  def deleteByLegalIdDirect(legalId: String): F[Boolean]

  def updateEverythingByLegalId(user:User): F[Boolean]

}