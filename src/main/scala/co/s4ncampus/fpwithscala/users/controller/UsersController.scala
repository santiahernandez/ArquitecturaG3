package co.s4ncampus.fpwithscala.users.controller

import co.s4ncampus.fpwithscala.users.domain._

import cats.effect.Sync
import cats.syntax.all._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl


import org.http4s.{EntityDecoder, HttpRoutes}

import co.s4ncampus.fpwithscala.users.domain.User

class UsersController[F[_]: Sync] extends Http4sDsl[F] {

    implicit val userDecoder: EntityDecoder[F, User] = jsonOf

    private def createUser(userService: UserService[F]): HttpRoutes[F] = 
        HttpRoutes.of[F] {
            case req @ POST -> Root =>
                val action = for {
                    user <- req.as[User]
                    result <- userService.create(user).value
                } yield result
                
                action.flatMap {
                    case Right(saved) => Ok(saved.asJson)
                    case Left(UserAlreadyExistsError(existing)) => Conflict(s"The user with legal id ${existing.legalId} already exists")
                }

        }
    private def findUser (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case GET -> Root / id =>
                val action = for {
                    result <- userService.findByLegalId(id).value
                } yield result
                action.flatMap {
                    case Right(saved) => Ok(saved.asJson)
                    case Left(UserDoesntExistError) => NotFound()
                }
        }

    private def deleteUser (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case DELETE -> Root / legalId =>
                val action = for {
                   result <- userService.deleteByLegalId(legalId).value
                } yield result
                action.flatMap {
                    case Right(_) => Ok()
                    case Left(UserDeleteFailed(_)) => Conflict(s"The user with legal id $legalId doesn't exists")
                }
        }

    private def updateUserPhone (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case req @PATCH -> Root / "phone" / legalId / phone =>
                val action = for {
                    user <- req.as[User]
                    result <- userService.updatePhoneBylegalId(legalId,phone).value
                } yield result
                action.flatMap {
                    case Right(saved) => Ok()
                    case Left(UserDoesntExistError) => NotFound(s"The user with legal id $legalId doesn't exists")
                }
        }

    private def updateUserEmail (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case PATCH -> Root / "email" / id / phone =>
                val action = for {
                    result <- userService.updateEmailByLegalId(id,phone).value
                } yield result
                action.flatMap {
                    case Right(saved) => Ok()
                    case Left(UserDoesntExistError) => NotFound(s"The user with legal id $id doesn't exists")
                }
        }

    private def updateUserName (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case PATCH -> Root / "name" / id / name =>
                val action = for {
                    result <- userService.updateNameByLegalId(id,name).value
                } yield result
                action.flatMap {
                    case Right(saved) => Ok()
                    case Left(UserDoesntExistError) => NotFound(s"The user with legal id $id doesn't exists")
                }
        }


    private def updateUserLastName (userService: UserService[F]): HttpRoutes[F] =
        HttpRoutes.of[F] {
            case PATCH -> Root / "lastName" / id / lastName =>
                val action = for {
                    result <- userService.updateLastNameByLegalId(id,lastName).value
                } yield result
                action.flatMap {
                    case Right(saved) => Ok()
                    case Left(UserDoesntExistError) => NotFound(s"The user with legal id $id doesn't exists")
                }
        }


    def endpoints(userService: UserService[F]): HttpRoutes[F] = {
        //To convine routes use the function `<+>`
        createUser(userService) <+> findUser(userService) <+> deleteUser(userService) <+> updateUserPhone(userService) <+>
          updateUserName(userService) <+> updateUserEmail(userService) <+> updateUserLastName(userService)
    }

}

object UsersController {
    def endpoints[F[_]: Sync](userService: UserService[F]): HttpRoutes[F] =
        new UsersController[F].endpoints(userService)
}