import org.apache.spark.sql.types.{StructField, StructType, StringType, LongType, IntegerType}
import org.apache.spark.sql.DataFrame
 
val myManualSchema = new StructType(Array(
  new StructField("color", StringType, false),
  new StructField("value1", IntegerType, false),
  new StructField("value2", IntegerType, false)
))
 
val data = spark.read.format("csv").schema(myManualSchema).option("header", "false").option("mode", "FAILFAST").load("/user/raj_ops/data.csv").cache()

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.clustering.{KMeans, KMeansModel}
import org.apache.spark.ml.feature.VectorAssembler

val numericOnly = data.cache()

val assembler = new VectorAssembler().setInputCols(Array("value1","value2")).setOutputCol("featureVector")

val kmeans = new KMeans().setPredictionCol("cluster").setFeaturesCol("featureVector").setK(3)


val pipeline = new Pipeline().setStages(Array(assembler, kmeans))

val pipelineModel = pipeline.fit(numericOnly)

val kmeansModel = pipelineModel.stages.last.asInstanceOf[KMeansModel]

kmeansModel.clusterCenters.foreach(println)

val withCluster = pipelineModel.transform(numericOnly)

//withCluster.select("cluster", "color").groupBy("cluster", "color").count().orderBy($"cluster", $"count".desc).show(25)
withCluster.show()
