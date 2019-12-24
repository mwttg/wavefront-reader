package org.sorted.chaos.wavefront.reader

import org.joml.Vector3f

/**
  * This model class represents the .mtl file, containing all relevant data
  *
  * @param ambientColor The ambient color (rgb)
  * @param diffuseColor The diffuse color (rgb)
  * @param specularColor the specular color (rgb)
  * @param specularExponent the specular exponent (between 0 and 1000)
  */
final case class WavefrontMaterial(
    ambientColor: Vector3f,
    diffuseColor: Vector3f,
    specularColor: Vector3f,
    specularExponent: Float
)

object WavefrontMaterial {
  import Color._
  import ExtractSingleFloat._

  private final val Ambient  = "Ka"
  private final val Diffuse  = "Kd"
  private final val Specular = "Ks"
  private final val Exponent = "Ns"

  def empty(): WavefrontMaterial = WavefrontMaterial(
    ambientColor     = new Vector3f(0.0f, 0.0f, 0.0f),
    diffuseColor     = new Vector3f(0.0f, 0.0f, 0.0f),
    specularColor    = new Vector3f(0.0f, 0.0f, 0.0f),
    specularExponent = 0.0f
  )

  def from(lines: Vector[String]): WavefrontMaterial =
    lines.foldLeft(WavefrontMaterial.empty()) { (accumulator, line) =>
      {
        val token = extractToken(line)
        processLine(accumulator, line, token)
      }
    }

  private def extractToken(line: String): String =
    line.take(2).trim

  private def processLine(accumulator: WavefrontMaterial, line: String, token: String) =
    token match {
      case Ambient  => accumulator.copy(ambientColor = line.getColor)
      case Diffuse  => accumulator.copy(diffuseColor = line.getColor)
      case Specular => accumulator.copy(specularColor = line.getColor)
      case Exponent => accumulator.copy(specularExponent = line.getSpecularExponent)
      case _        => accumulator
    }
}
