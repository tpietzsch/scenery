package scenery

import cleargl.GLMatrix
import com.jogamp.opengl.GL
import java.nio.FloatBuffer
import java.util.*

/**
 * Created by ulrik on 18/01/16.
 */
class Sphere(radius: Float, segments: Int) : GeometricalObject(3, GL.GL_TRIANGLE_STRIP){
    var radius: Float
    var segments: Int

    var vertices: FloatArray? = null
    var normals: FloatArray? = null

    init {
        this.radius = radius
        this.segments = segments

        var vbuffer = ArrayList<Float>()
        var nbuffer = ArrayList<Float>()

        for(i: Int in 1..segments) {
            val lat0: Float = Math.PI.toFloat()  * (-0.5f + (i.toFloat()-1.0f)/segments.toFloat());
            val lat1: Float = Math.PI.toFloat() * (-0.5f + i.toFloat()/segments.toFloat());

            val z0 = Math.sin(lat0.toDouble()).toFloat()
            val z1 = Math.sin(lat1.toDouble()).toFloat()

            val zr0 = Math.cos(lat0.toDouble()).toFloat()
            val zr1 = Math.cos(lat1.toDouble()).toFloat()

            for (j: Int in 1..segments) {
                val lng = 2 * Math.PI.toFloat() * (j - 1) / segments
                val x = Math.cos(lng.toDouble()).toFloat()
                val y = Math.sin(lng.toDouble()).toFloat()

                vbuffer.add(x * zr0 * radius)
                vbuffer.add(y * zr0 * radius)
                vbuffer.add(z0 * radius)

                vbuffer.add(x * zr1 * radius)
                vbuffer.add(y * zr1 * radius)
                vbuffer.add(z1 * radius)

                nbuffer.add(x)
                nbuffer.add(y)
                nbuffer.add(z0)

                nbuffer.add(x)
                nbuffer.add(y)
                nbuffer.add(z1)
            }
        }

        vertices = vbuffer.toFloatArray()
        normals = nbuffer.toFloatArray()
    }

    override fun init(): Boolean {
        // null GLSL program, aka use the default shaders
        val program = null

        super.init()

        setVerticesAndCreateBuffer(FloatBuffer.wrap(vertices))
        setNormalsAndCreateBuffer(FloatBuffer.wrap(normals))

        this.model = GLMatrix.getIdentity()

        return true
    }
}