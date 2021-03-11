package co.s4ncampus.fpwithscala.users.domain

import cats.data._
import cats.{Functor, Monad}

class UserService[F[_]](repository: UserRepositoryAlgebra[F], validation: UserValidationAlgebra[F]) {

  def create(user: User)(implicit M: Monad[F]): EitherT[F, UserAlreadyExistsError, User] =
    for {
      _ <- validation.doesNotExist(user)
      saved <- EitherT.liftF(repository.create(user))
    } yield saved

  def findByLegalId(legalId: String)(implicit F: Functor[F]): EitherT[F, UserDoesntExistError.type, User] =
    repository.findByLegalId(legalId).toRight(UserDoesntExistError)


  def deleteByLegalId(user: User)(implicit F: Monad[F]): EitherT[F, UserDeleteFailed, Boolean] =
    EitherT.liftF(repository.deleteByLegalId(user))

  def deleteByLegalIdDirect(legalId: String)(implicit F: Monad[F]): EitherT[F, UserDeleteFailed, Boolean] =
    EitherT.liftF(repository.deleteByLegalIdDirect(legalId))


  def updateEverythingBylegalId(user: User)(implicit F: Monad[F]):EitherT[F, UserDoesntExistError, Boolean] =
  EitherT.liftF(repository.updateEverythingByLegalId(user))

  /*
  def deleteByLegalId(legalId: String)(implicit F: Functor[F]): EitherT[F, UserDoesntExistError.type, User] = for {
    saved <- repository.deleteByLegalId(legalId).toRight(UserDoesntExistError)
  }yield saved*/

}

object UserService {
  def apply[F[_]](
                   repositoryAlgebra: UserRepositoryAlgebra[F],
                   validationAlgebra: UserValidationAlgebra[F],
                 ): UserService[F] =
    new UserService[F](repositoryAlgebra, validationAlgebra)
}