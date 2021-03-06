package data

import controllers.VehicleSearchForm
import models.{VehicleMake, Vehicle}

object VehicleRepository {
  private lazy val entireUkVehicleDatabase = List(
    Vehicle("HK55 TFU", VehicleMake("Land Rover"), 3230),
    Vehicle("FN02 KTD", VehicleMake("Audi"), 1500),
    Vehicle("MC65 MCL", VehicleMake("McLaren"), 1500)
  )

  lazy val makes = entireUkVehicleDatabase.map(_.make.name).distinct.sorted

  def search(searchForm: VehicleSearchForm): Option[Vehicle] =
    entireUkVehicleDatabase.find { vehicle =>
      vehicle.numberPlate == searchForm.numberPlate &&
        vehicle.make.name == searchForm.make
    }
}