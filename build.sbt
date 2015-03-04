name := """poc-scala"""

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq(
    "jets3t" at "http://www.jets3t.org/maven2"
)

libraryDependencies ++= Seq (
	"org.apache.camel" % "camel-scala" % "2.14.1",
	"net.java.dev.jets3t" % "jets3t" % "0.9.0"
)


