import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object AddAliasesApp {
  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .master("local[*]")
      .getOrCreate()

    import session.implicits._

    val data: Dataset[Row] = Seq(
      ("10", "20", "30"),
      ("10", "20", "30"),
      ("10", "20", "30"),
      ("10", "20", "30"),
      ("10", "abc", "30"),
      ("10", "20", "30"),
      ("10", "20", "30"),
      ("10", "20", "30")
    ).toDF("arg0", "arg1", "arg2")

    val result = data.select(
      when(
        not(data.col("arg0").isNaN),
        data.col("arg0")
      ) cast IntegerType as "arg0",
      when(not(data.col("arg1").isNaN),
        data.col("arg1")
      ) cast IntegerType as "arg1",
      when(not(data.col("arg2").isNaN),
        data.col("arg2")
      ) cast IntegerType as "arg2"
    )

    result.printSchema()

    /* prints:
     *  root
     *   |-- arg0: integer (nullable = true)
     *   |-- arg1: integer (nullable = true)
     *   |-- arg2: integer (nullable = true)
     */

    session.stop()
  }
}
