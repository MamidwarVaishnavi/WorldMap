package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import service._
import service.JsonReadWrites._
import service.City._
import service.ResponseModels.AddCity
import service.ResponseModels.AddCity._
import play.api.Logger
import scala.concurrent.Future
import play.api.mvc.Result._


@Singleton
class HomeController @Inject()(cc: ControllerComponents,worldService: WorldService) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("hello")}

  def hello(name: String) = Action {
    Ok("Hello"+" "+ name)}

  def addContinent = Action {
    request => val  json = request.body.asJson.get
      val continent  = json.as[Continent]
      service.WorldService.world.continents += continent
      println(continent)
      Ok("success")}

  def delContinent=Action{
    request => val  json = request.body.asJson.get
      val continent  = json.as[Continent]
      service.WorldService.world.continents -= continent
      println(continent)
      Ok("success")
  }

  def addCity = Action{
    request=>val json=request.body.asJson.get
      val AddCity(country,cities) = json.as[AddCity]
      worldService.validateCountry(country).headOption match {
        case Some(countryData) => Ok(Json.toJson(ResponseModels.CitiesList(
          worldService.addCities(countryData, cities))))

        case None => BadRequest("please provide correct country")
      }}


  def getContinentofCountry(country: String) = Action {
    Ok(worldService.getContinentbYPassingCountry(country))
  }

  def getContries(continent: String) = Action {
    import ResponseModels.ContriesList.ContriesListWrites
    worldService.validateContinent(continent).headOption match {
      case Some(continentData) => Ok(Json.toJson(ResponseModels.ContriesList(
        worldService.getCountriesBypassingContinent(continentData))))
      case None => BadRequest("Please Input the correct continent")
    }}


  def CountryLieInSameContinent(first: String,second: String)= Action{
    Ok(worldService.findIfTwoCountryLieInSameContinent(first,second))
 }

  def cities(country:String)=Action{
    import ResponseModels.CitiesList.CitiesListWrites
  Ok(Json.toJson(ResponseModels.CitiesList(worldService.getCitiesBypassingCountry(country))))
}

  def getcitiesofcontinent(continent:String)=Action{
    Ok(Json.toJson(ResponseModels.CitiesList(worldService.getCitiesBypassingContinent(continent))))
  }

  def cityOnFirstLetter(country:String)=Action{
    Ok(Json.toJson((worldService.getCitiesOnFirstLetter(country))))
  }

  def getcityacrossgloble()=Action{
    Ok(Json.toJson((worldService.GroupCitiesByFirstLetterAcrossTheGlobe())))
  }
}
