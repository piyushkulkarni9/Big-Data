import org.apache.spark.sql.types.{StructField, StructType, StringType, LongType, IntegerType, FloatType, DoubleType}
import org.apache.spark.sql.DataFrame
 
val myManualSchema = new StructType(Array(
  new StructField("groupNum", IntegerType, false),
  new StructField("randomIntValue", IntegerType, false),
  new StructField("anIntegerField", IntegerType, false),
  new StructField("aFloatField", FloatType, false),
  new StructField("aDoubleField", DoubleType, false)
))
 
val tsvFile = spark.read.format("csv").schema(myManualSchema).option("sep", "\t").option("header", "false").option("mode", "FAILFAST").load("/user/raj_ops/tsvdata.tsv")
