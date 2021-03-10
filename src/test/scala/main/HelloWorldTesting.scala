package main

import org.scalatest.FunSuite

class HelloWorldTesting extends FunSuite{
  test("testing hello world"){
    assert(HelloWorld.Hello("santiago") === "hello santiago")
  }
}
