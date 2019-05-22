package service
import scala.collection.mutable.ListBuffer
import play.api.libs.json._



object ResponseModels {

  case class ContriesList(countries: ListBuffer[String])

  case object ContriesList {
    implicit val  ContriesListWrites = Json.writes[ContriesList]}


  case class CitiesList(city:ListBuffer[String])
  case object  CitiesList{
    implicit val CitiesListWrites= Json.writes[CitiesList]
  }

  case class GetCityListOnFirstLetter(firstLetter:String)
  case object GetCityListOnFirstLetter{
    implicit val getCityList=Json.writes[GetCityListOnFirstLetter]
  }


  case class Data(firstLetter:String, cities: Seq[String])
  object Data {
    implicit val dataReads = Json.reads[Data]
    implicit val dataWrites = Json.writes[Data]
  }

  import service.City._

  case class AddCity(country:String,cities:Seq[City])
  object AddCity {
    implicit val dataReads = Json.reads[AddCity]
    implicit val dataWrites = Json.writes[AddCity]
 }}