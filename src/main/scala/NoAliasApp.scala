import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object NoAliasApp {
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
      ) cast IntegerType,
      when(not(data.col("arg1").isNaN),
        data.col("arg1")
      ) cast IntegerType,
      when(not(data.col("arg2").isNaN),
        data.col("arg2")
      ) cast IntegerType
    )

    result.printSchema()

    /* prints:
     * root
     *  |-- CAST(CASE WHEN (NOT isnan(arg0)) THEN arg0 END AS INT): integer (nullable = true)
     *  |-- CAST(CASE WHEN (NOT isnan(arg1)) THEN arg1 END AS INT): integer (nullable = true)
     *  |-- CAST(CASE WHEN (NOT isnan(arg2)) THEN arg2 END AS INT): integer (nullable = true)
     */

    session.stop()
  }
}
