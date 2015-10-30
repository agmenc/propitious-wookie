package dvla

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class PageFlowSpec extends Specification {

  "Application" should {
    "Have a home page" in new WithApplication {
      val indexPage = route(FakeRequest(GET, "/")).get

      status(indexPage) must equalTo(OK)
      contentAsString(indexPage) must contain("Details of the vehicle being checked")
    }

    "Return 404 for non-existent pages" in new WithApplication {
      status(route(FakeRequest(GET, "/speedCameraLocations")).get) === NOT_FOUND
    }

    "Report errors when required fields are missing" in new WithApplication {
      val searchResultsPage = route(FakeRequest(POST, "/search").withFormUrlEncodedBody("numberPlate" -> "", "make" -> "")).get

      contentAsString(searchResultsPage) must contain("This field is required")
    }

    "Display the vehicle's details if it can be found" in new WithApplication {
      val searchResultsPage = route(FakeRequest(POST, "/search").withFormUrlEncodedBody("numberPlate" -> "HK55 TFU", "make" -> "Land Rover")).get

      contentAsString(searchResultsPage) must contain("Number Plate: HK55 TFU")
      contentAsString(searchResultsPage) must contain("Make: Land Rover")
      contentAsString(searchResultsPage) must contain("Revenue weight: 3230kg")
    }

    // "Display a vehicle-not-found page if nothing in the DB matches the search terms"
    // Return a not-found view, as per https://www.vehicleenquiry.service.gov.uk/
  }
}
