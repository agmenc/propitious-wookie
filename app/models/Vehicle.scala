package models

case class VehicleMake(name: String)

case class Vehicle(numberPlate: String, make: VehicleMake)

object Vehicle {
  val makes = List(
    VehicleMake("Land Rover"),
    VehicleMake("McLaren"),
    VehicleMake("Audi")
  )
}

