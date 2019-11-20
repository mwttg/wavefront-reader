package org.sorted.chaos.utilities

import org.scalatest.{Matchers, WordSpec}

class FileReaderTest extends WordSpec with Matchers {
  "FileReader" should {
    "read a text file" in {
      val actual = FileReader.read("text.txt")
      actual shouldBe Vector("1. line", "2. line", "end of file")
    }
    "return empty Vector if file does not exist" in {
      val actual = FileReader.read("file-does-not-exist.txt")
      actual shouldBe Vector.empty[String]
    }
  }
}