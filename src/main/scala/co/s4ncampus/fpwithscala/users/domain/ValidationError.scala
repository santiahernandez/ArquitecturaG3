package co.s4ncampus.fpwithscala.users.domain

sealed trait ValidationError extends Product with Serializable

case class UserAlreadyExistsError(user: User) extends ValidationError

case class UserDoesntExistError(legalId: String) extends ValidationError

case class UserDeleteFailed(legalId: String) extends ValidationError

//todo: Creacion clases de error.
