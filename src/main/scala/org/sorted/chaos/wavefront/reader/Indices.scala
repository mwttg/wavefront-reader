package org.sorted.chaos.wavefront.reader

/**
  * This model class represents indices (vertex, texture, normal) for one point of a triangle.
  *
  * @param vertexIndex the index of the vertex
  * @param textureIndex the index of the texture (optional)
  * @param normalIndex the index of the normal (optional)
  * @param tangentsIndex the index of the tangent (optional) used for normal mapping
  * @param biTangentsIndex the index of the normal (optional) used for normal mapping
  */
final case class Indices(
    vertexIndex: Int,
    textureIndex: Option[Int],
    normalIndex: Option[Int],
    tangentsIndex: Option[Int],
    biTangentsIndex: Option[Int]
)

object Indices {
  private final val VertexTextureNormalPattern = """(\d+)/(\d+)/(\d+)""".r
  private final val VertexTexturePattern       = """(\d+)/(\d+)""".r
  private final val VertexNormalPattern        = """(\d+)//(\d+)""".r
  private final val VertexPattern              = """(\d+)""".r

  implicit class ExtractIndicesFrom(val linePart: String) {
    def getIndices: Option[Indices] =
      linePart match {
        case VertexTextureNormalPattern(vertex, texture, normal) =>
          Some(
            Indices(
              vertexIndex     = vertex.toInt,
              textureIndex    = Some(texture.toInt),
              normalIndex     = Some(normal.toInt),
              tangentsIndex   = None,
              biTangentsIndex = None
            )
          )
        case VertexTexturePattern(vertex, texture) =>
          Some(
            Indices(
              vertexIndex     = vertex.toInt,
              textureIndex    = Some(texture.toInt),
              normalIndex     = None,
              tangentsIndex   = None,
              biTangentsIndex = None
            )
          )
        case VertexNormalPattern(vertex, normal) =>
          Some(
            Indices(
              vertexIndex     = vertex.toInt,
              textureIndex    = None,
              normalIndex     = Some(normal.toInt),
              tangentsIndex   = None,
              biTangentsIndex = None
            )
          )
        case VertexPattern(vertex) =>
          Some(
            Indices(
              vertexIndex     = vertex.toInt,
              textureIndex    = None,
              normalIndex     = None,
              tangentsIndex   = None,
              biTangentsIndex = None
            )
          )
        case _ => None
      }
  }
}
