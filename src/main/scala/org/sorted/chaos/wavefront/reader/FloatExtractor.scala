package org.sorted.chaos.wavefront.reader

import org.sorted.chaos.wavefront.reader.Wavefront.Space

final case class ExtractTuple(lineParts: Array[String], numbers: Array[Float])

/**
  * This trait is used to extract Floats from a line of an .obj or a .mtl file.
  * The line must contain a TOKEN and at least one Float number.
  * E.g.
  *   a vertex line:            'v 1.0 1.0 1.0'
  *   a diffuse color line:     'Kd 0.3 0.4 0.5'
  *   a texture line:           'vt 1.2 2.4
  *   a specular exponent line: 'Ns 12.4'
  *
  */
trait FloatExtractor {
  protected def extract(line: String): ExtractTuple = {
    val lineParts = line.split(Space)
    val numbers   = lineParts.tail.flatMap(_.toFloatOption)

    ExtractTuple(lineParts, numbers)
  }
}
