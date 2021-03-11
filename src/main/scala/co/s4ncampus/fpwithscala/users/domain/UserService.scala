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


  def deleteByLegalId(legalId: String)(implicit F: Functor[F]): EitherT[F, UserDeleteFailed, Int] =
    EitherT.liftF(repository.deleteByLegalId(legalId))



  def updatePhoneBylegalId(legalId:String, phone:String)(implicit F: Monad[F]): EitherT[F, UserDoesntExistError.type, Int] =  {
    EitherT.liftF(repository.updatePhoneByLegalId(legalId,phone))
  }
/*
  def deleteByLegalId(legalId: String)(implicit F: Functor[F]): EitherT[F, UserDoesntExistError.type, User] = for {
    saved <- repository.deleteByLegalId(legalId).toRight(UserDoesntExistError)
  }yield saved
*/
  // todo: Creacion de funciones update, delete, read
}

object UserService {
  def apply[F[_]](
                   repositoryAlgebra: UserRepositoryAlgebra[F],
                   validationAlgebra: UserValidationAlgebra[F],
                 ): UserService[F] =
    new UserService[F](repositoryAlgebra, validationAlgebra)
}