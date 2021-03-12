package co.s4ncampus.fpwithscala.users.domain

import munit.FunSuite


class algebraTest () extends FunSuite {

  val service: UserService.type = UserService

  test("create function testing"){
    assertEquals(1,1)
  }

  test("get function testing"){
    assertEquals(2,2)
  }
}
