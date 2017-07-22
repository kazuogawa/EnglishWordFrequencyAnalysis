import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sample0722spark {
  def main(args: Array[String]): Unit = {
    //path書き換えてね
    val path = """src\main\resources\Gulliver's travels.txt"""
    require(path != "",
      "ドライバプログラムの引数に単語をカウントするファイルパスを指定してください\n" +
      """windowsの例:src\main\resources\sampletext.txt"""
    )
    val conf = new SparkConf
    conf.setAppName("sample0722spark")
    conf.setMaster("local")
    val sc   = new SparkContext(conf)

    try{
      val wordAndCountRDD = sc.textFile(path)
                                //正規表現でスペース、カンマ、コロンで文を単語に分割
                               .flatMap(_.split("[ ,.]"))
                                //Alphabetとnumで書かれている物だけを抽出
                               .filter(_.matches("""\p{Alnum}+"""))
                                //(単語,1)のタプルに変換
                               .map((_, 1))
                                //単語をkeyとして、単語の個数をカウント
                               .reduceByKey(_ + _)
                                //単語の頻出数でsortをするために(単語,頻出数)から(頻出数,単語)にする必要がある
                               .map{wordAndCount => (wordAndCount._2,wordAndCount._1)}
                                //並び替え
                               .sortByKey(false)
                                //入れ替えの書き方としてこういうこともできる
                               .map{case (count, word) => (word, count)}.collect
      println("全データ")
      wordAndCountRDD.foreach(println)
      println("top3:")
      wordAndCountRDD.take(3).foreach(println)
    }
    finally {
      sc.stop()
    }
  }
  //tapleExchange(パラメータ)のような使い方をすれば入れ替えに使えるとけど、.の連結処理には使えない。つらい
  def tapleExchange[K,V](rddData:RDD[(K, V)]):RDD[(V, K)] = rddData.map{rddParam => (rddParam._2, rddParam._1)}
}
