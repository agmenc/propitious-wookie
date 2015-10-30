package data

import models.{VehicleMake, Vehicle}

object VehicleRepository {
  private val entireUkVehicleDatabase = List(
    Vehicle("HK55 TFU", VehicleMake("Land Rover")),
    Vehicle("FN02 KTD", VehicleMake("Audi")),
    Vehicle("MC65 MCL", VehicleMake("McLaren"))
  )

  def find(numberPlate: String): Option[Vehicle] = ???
}