package com.nitrograph.dotty.language.feature
package types

import scala.util.Try
import scala.collection.immutable.{Range, Map}

case class IntersectionTypes(
    examples: Map[String, () => Unit],
    tests: Map[String, () => Try[Nothing]]
) extends DottyFeature(
    name = "Intersection types",
    List(
        "Main" -> IntersectionTypes.Example.Main
    ).toMap,
    tests
)

object IntersectionTypes {
    object Example {
        val Main = () => {
            case class Vertex2d(x: Double, y: Double) {
                def radius: Double = {
                    Math.sqrt(x * x + y * y)
                }
            }

            object Shape {
                trait Perimeter {
                    def accurate: Double
                    def rough: Double
                }

                trait Sector {
                    def accurate: Double
                    def rough: Double
                }
            }

            sealed trait Shape {
                val Perimeter: Object with Shape.Perimeter
                val Sector: Object with Shape.Sector
            }

            trait Polygon(val vertices: Array[Vertex2d])
                extends Shape
            { polygon =>

                require(polygon.Perimeter.accurate > 0)
                require(polygon.Sector.accurate > 0)

                def metric(from: Vertex2d, to: Vertex2d): Double = {
                    Math.sqrt(Math.pow(to.x - from.x, 2.0) + Math.pow(to.y - from.y, 2.0))
                }

                object Perimeter
                    extends Shape.Perimeter
                {
                    def accurate: Double = {
                        (
                            for {
                                index <- Range(0, polygon.vertices.length)
                            } yield {
                                val a = vertices(index)
                                val b = vertices((index + 1) % polygon.vertices.length)
                                polygon.metric(a, b)
                            }
                        ).toArray.foldLeft(0.0)(_ + _)
                    }

                    def rough: Double = Perimeter.accurate
                }

                object Sector
                    extends Shape.Sector
                {
                    /*
                    *              |x(0) x(1) x(2) ... x(i) x(i + 1) ... x(N)|
                    * S(Polygon) = |y(0) y(1) y(2) ... y(i) y(i + 1) ... y(N)| =
                    *
                    *            = [x(0) * y(1) - x(1) * y(0)] + ... + [x(i) * y(i + 1) - x(i + 1) * y(i)] + ... + [x(N) * y(0) - x(0) * y(N)]
                    */
                    def accurate: Double = {
                        (
                            for {
                                index <- Range(0, polygon.vertices.length)
                            } yield {
                                val a = vertices(index)
                                val b = vertices((index + 1) % polygon.vertices.length)
                                a.x * b.y - a.y * b.x
                            }
                        ).toArray.foldLeft(0.0)(_ + _)
                    }

                    def rough: Double = Perimeter.accurate
                }
            }

            trait Circle(val center: Vertex2d, val radius: Double)
                extends Shape
            { circle =>
                require(circle.radius > 0)

                def sampled(sampleRate: Double): Polygon = {
                    require(sampleRate >= .0 && sampleRate <= 1.0)

                    new Polygon(
                        (
                            for {
                                index <- 0 to (1.0 / sampleRate).toInt
                            } yield Vertex2d(
                                radius * Math.cos(index * 2.0 * Math.PI),
                                radius * Math.sin(index * 2.0 * Math.PI)
                            )
                        ).toArray
                    ) {}
                }

                object Perimeter
                    extends Shape.Perimeter
                {
                    def accurate: Double = 2.0 * Math.PI * circle.radius

                    def rough: Double = circle.sampled(0.01).Perimeter.accurate
                }

                object Sector
                    extends Shape.Sector
                {
                    def accurate: Double = Math.PI * Math.pow(circle.radius, 2.0)
                    def rough: Double = circle.sampled(0.01).Sector.accurate
                }
            }

            println("Polygon(ABC):")

            val triangleVertices = Array(
                Vertex2d(  .0,   .0),
                Vertex2d(10.0, 10.0),
                Vertex2d(10.0,   .0)
            )

            triangleVertices.zip(
                Array("A", "B", "C")
            ).foreach {
                case (Vertex2d(x, y), vertexLabel) =>
                    println(s"$vertexLabel($x, $y)")
            }

            val triangle: Shape = new Polygon(triangleVertices) {}
            println("ABC sector:")
            println(s"${triangle.Sector.accurate}")

            val circle: Shape = new Circle(Vertex2d(.0, .0), 1.0) {}
            println("Circle sector:")
            println(s"${circle.Sector.accurate}")
        }
    }
}
