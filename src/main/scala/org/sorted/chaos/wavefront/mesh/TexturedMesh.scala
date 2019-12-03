package org.sorted.chaos.wavefront.mesh

import org.sorted.chaos.wavefront.reader.Wavefront

/**
  * This model class represents a Mesh with vertices and a texture, but without a index list
  *
  * @param vertices the array of vertices for the VertexBufferObject
  * @param textures the array of UV coordinates for the VertexBufferObject
  */
final case class TexturedMesh(vertices: Array[Float], textures: Array[Float])

object TexturedMesh extends Mesh {

  private def empty = TexturedMesh(Array.emptyFloatArray, Array.emptyFloatArray)

  def from(wavefront: Wavefront): TexturedMesh = {
    val wavefrontVertices  = wavefront.vertices
    val wavefrontTextures  = wavefront.textures
    val wavefrontTriangles = wavefront.triangles

    wavefrontTriangles.foldLeft(TexturedMesh.empty) { (accumulator, triangle) =>
      {
        val vertices = getVerticesOfTriangle(triangle, wavefrontVertices)
        val textures = getTexturesOfTriangle(triangle, wavefrontTextures)

        TexturedMesh(
          vertices = accumulator.vertices ++ vertices,
          textures = accumulator.textures ++ textures
        )
      }
    }
  }
}