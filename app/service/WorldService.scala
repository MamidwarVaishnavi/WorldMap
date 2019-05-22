package service

import scala.collection.immutable
import scala.collection.mutable.ListBuffer
import play.api.libs.json._


case class World(continents:ListBuffer[Continent])
case class Continent(name: String, countries:ListBuffer[Country])
case class Country(cname:String,cities: ListBuffer[City])
case class City(name :String)

case object City {
  implicit val cityReads = Json.reads[City]
  implicit val cityWrites = Json.writes[City]
}

import service.ResponseModels.Data
import service.ResponseModels.AddCity

object  WorldService {
  val citiesInIndia = ListBuffer(City("Hyderabad"), City("Nanded"), City("Nagpur"), City("Pune"), City("Mumbai"))
  val citiesInBangladesh = ListBuffer(City("Dhaka"), City("Chittagong"), City("Khulna"))
  val citiesInAlgeria = ListBuffer(City("AA"), City("BB"))
  val citiesInCameroon = ListBuffer(City("XX"), City("YY"))

  var India = Country("India", citiesInIndia);
  var Bangladesh = Country("Bangladesh", citiesInBangladesh);
  var Algeria = Country("Algeria", citiesInAlgeria)
  var Cameroon = Country("Cameroon", citiesInCameroon)

  var Asia = Continent("Asia", ListBuffer(India, Bangladesh));
  var Africa = Continent("Africa", ListBuffer(Algeria, Cameroon))


  var world = World(ListBuffer(Asia, Africa));
}

class WorldService {

   import WorldService._
  def findIfTwoCountryLieInSameContinent(first: String, second: String): String = {
//    ( (for {
//      cntry <- world.continents
//      c <- cntry.countries
//      if (c.cname == first && c.cname == second)
//    } yield cntry.name ).distinct.size == 1).toString


    var d=(for {
      cntry <- world.continents
      c <- cntry.countries
      if (c.cname == first)
    } yield cntry.name)
    var t=for {
      cntry <- world.continents
      c <- cntry.countries
      if (c.cname == second)
    } yield cntry.name

    if(d.isEmpty && t.isEmpty)
      return  false.toString
    else if(d == t)
      return  true.toString
    else
      return  false.toString

  }


  def getContinentbYPassingCountry(country: String): String = try{
    (for {
      cntry <- world.continents
      c <- cntry.countries
      if (c.cname == country)
    } yield cntry.name).head
  }
  catch{case _:Throwable=>"please input the correct country"}


  def validateContinent(continent: String) = {
    val d = for {
      continentP <- world.continents
      if (continentP.name == continent)
    } yield continentP
    d
  }

  def getCountriesBypassingContinent(continent: Continent): ListBuffer[String] = {
    continent.countries.map(_.cname)
  }


  def validateCountry(countryData:String)={
   val c  =  for{
      continentp<-world.continents
      country<-continentp.countries
      _ = println(country.cname == countryData)
      if(country.cname == countryData)
    }yield country
    c
  }

def addCities(country:Country,cities:Seq[City]):ListBuffer[String]={
  country.cities ++=cities
  country.cities.map(_.name)
}

  def getCitiesBypassingCountry(country: String): ListBuffer[String] = {

    for {
      continentP <- world.continents
      countryp <- continentP.countries
      if (countryp.cname == country)
      city <- countryp.cities
    } yield city.name
  }

  def getCitiesOnFirstLetter(country: String) = {
   val d= (for {
      continentP <- world.continents
      countryp <- continentP.countries
      if (countryp.cname == country)
      city <- countryp.cities
    }yield city.name).groupBy(_.charAt(0))
    d.map {case (k,v) => Data(k.toString,v)}
  }

  def getCitiesBypassingContinent(Continent:String): ListBuffer[String] ={

    for{
      continentp<-world.continents
      if(continentp.name == Continent)
      country<-continentp.countries
      city<-country.cities
    }yield city.name}


  def GroupCitiesByFirstLetterAcrossTheGlobe()={
    val d=(for{
      continentP <- world.continents
      countryp <- continentP.countries
      city <- countryp.cities
    }yield city.name).groupBy(_.charAt(0))
    d.map{case (k, v) => Data(k.toString,v)}
  }
}



object JsonReadWrites {

  import play.api.libs.json._

  implicit val cityWrites = Json.writes[City]
  implicit val cityReads = Json.reads[City]

  implicit val countryWrites = Json.writes[Country]
  implicit val countryReads = Json.reads[Country]

  implicit val continentWrites = Json.writes[Continent]
  implicit val continentReads=Json.reads[Continent]
}