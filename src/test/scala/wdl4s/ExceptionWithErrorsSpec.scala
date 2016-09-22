package wdl4s

import org.scalatest.{Matchers, FlatSpec}

import scalaz.NonEmptyList

class ExceptionWithErrorsSpec extends FlatSpec with Matchers {

  "ExceptionWithErrors" should "aggregate errors in getMessage method" in {
    val exception = new RuntimeException with ExceptionWithErrors {
      val message = "This is absolutely NOT working."
      val errors = NonEmptyList("because of A", "and also B", "and maybe C")
    }

    exception.getMessage shouldBe
      """This is absolutely NOT working.
        |because of A
        |and also B
        |and maybe C""".stripMargin
  }

}
