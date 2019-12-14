package org.sorted.chaos.wavefront.reader

/**
  * This model class represents the .obj file, containing all the relevant data
  *
  * @param vertices a list of vertex definitions
  * @param triangles a list of triangle definitions
  * @param normals a list of normal definitions
  * @param textures a list of texture definitions
  */
final case class Wavefront(
    vertices: Vector[Point],
    triangles: Vector[Triangle],
    normals: Vector[Point],
    textures: Vector[UVCoordinate]
)

object Wavefront {
  import Point._
  import Triangle._
  import UVCoordinate._

  private[reader] final val Space = " "

  private final val Vertex  = "v"
  private final val Face    = "f"
  private final val Texture = "vt"
  private final val Normal  = "vn"

  private def empty =
    Wavefront(
      vertices  = Vector.empty[Point],
      triangles = Vector.empty[Triangle],
      normals   = Vector.empty[Point],
      textures  = Vector.empty[UVCoordinate]
    )

  def from(lines: Vector[String]): Wavefront =
    lines.foldLeft(Wavefront.empty) { (accumulator, line) =>
      {
        val token = extractToken(line)
        processLine(accumulator, line, token)
      }
    }

  private def extractToken(line: String) =
    line.take(2).trim

  private def processLine(accumulator: Wavefront, line: String, token: String): Wavefront =
    token match {
      case Vertex  => accumulator.copy(vertices = accumulator.vertices :+ line.getPoint)
      case Face    => accumulator.copy(triangles = accumulator.triangles :+ line.getTriangle)
      case Texture => accumulator.copy(textures = accumulator.textures :+ line.getUVCoordinate)
      case Normal  => accumulator.copy(normals = accumulator.normals :+ line.getPoint)
      case _       => accumulator
    }
}
