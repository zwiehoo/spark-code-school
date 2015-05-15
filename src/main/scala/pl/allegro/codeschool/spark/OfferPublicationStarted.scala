package pl.allegro.codeschool.spark

import com.lambdaworks.jacks.JacksMapper
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils

object OfferPublicationStarted {
  def stream(ssc: StreamingContext): ReceiverInputDStream[(String, String)] = {
    KafkaUtils.createStream(ssc,
      "zookeeper.qxlint:2181/external/kasia/kafka",
      "pl.spark.training.zwieslaw",
      Map("kasia.pl.allegro.offercore.api.offerPublicationStarted" -> 4)
    )
  }

  def message(tuple: (String, String)): String = tuple match {
    case (_, message) => message
  }

  def parse(message: String): Map[String, Any] = {
    JacksMapper.readValue[Map[String, Any]](message)
  }
}

object Test extends App {
  val s = """{"offerId":"5286847946","eventTimestamp":"1429598633","eventMicroTimestamp":"1429598633053319","producer":null,"name":"Артисты кино. Екатерина Васильева Щ","categoriesPath":[{"id":48341,"name":"Коллекционирование "},{"id":48537,"name":"Открытки "},{"id":48542,"name":"СССР 1970-1979 года"}],"publication":{"startingTime":"2015-04-21T06:38:17Z","endingTime":"2015-05-01T06:38:17Z"},"vendor":null,"url":"http://molotok.ru/artisty-kino-ekaterina-vasil-eva-s-i5286847946.html","types":["AUCTION"],"quantityType":"UNIT","buyNow":null,"auction":{"currentPrice":1.00,"minimalPrice":0.00,"startingPrice":1.00},"condition":null,"photos":[[{"url":"http://img01.allegroimg.pl/photos/oryginal/52/86/84/79/5286847946","dimensions":"original"},{"url":"http://img01.allegroimg.pl/photos/64x48/52/86/84/79/5286847946","dimensions":"64x48"},{"url":"http://img01.allegroimg.pl/photos/400x300/52/86/84/79/5286847946","dimensions":"400x300"},{"url":"http://img01.allegroimg.pl/photos/192x144/52/86/84/79/5286847946","dimensions":"192x144"},{"url":"http://img01.allegroimg.pl/photos/128x96/52/86/84/79/5286847946","dimensions":"128x96"}],[{"url":"http://img01.allegroimg.pl/photos/64x48/52/86/84/79/5286847946_1","dimensions":"64x48"},{"url":"http://img01.allegroimg.pl/photos/oryginal/52/86/84/79/5286847946_1","dimensions":"original"},{"url":"http://img01.allegroimg.pl/photos/128x96/52/86/84/79/5286847946_1","dimensions":"128x96"},{"url":"http://img01.allegroimg.pl/photos/400x300/52/86/84/79/5286847946_1","dimensions":"400x300"},{"url":"http://img01.allegroimg.pl/photos/192x144/52/86/84/79/5286847946_1","dimensions":"192x144"}]],"sellerId":9391463,"countryIsoCode":"RU","sellerCountryIsoCode":"RU","attributes":[],"descriptions":{"original":"http://allegro.pl/offerDescription/5286847946/original","simplified":"http://allegro.pl/offerDescription/5286847946/simplified"},"shipping":{"isFreeReturn":false,"isFreeShipping":false,"lowestShippingCost":50.00,"shippingOptions":[{"shippingTypeId":1,"firstItemCost":50.00,"nextItemCost":50.00,"packageSize":1},{"shippingTypeId":5,"firstItemCost":50.00,"nextItemCost":50.00,"packageSize":1}]},"location":"Ижевск","stock":{"quantityAvailable":1,"quantitySold":0},"promotionOptions":[],"flags":[],"eventTopicName":"pl.allegro.offercore.api.offerPublicationStarted"}"""
}