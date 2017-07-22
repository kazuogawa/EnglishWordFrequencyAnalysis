name := "sample0722spark"

version := "1.0"

//しっかりlibraryのversionとscalaのversionを合わせよう
scalaVersion := "2.11.7"
//scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  // https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.11
  "org.apache.spark" %% "spark-core" % "2.1.0"
  //"org.apache.spark" %% "spark-core" % "2.2.0"
  //  "org.apache.spark" % "spark-mllib_2.10" % "2.1.0",
  //  "org.apache.spark" % "spark-sql_2.10" % "2.1.0",
  //  "com.databricks" % "spark-csv_2.10" % "1.4.0"
)