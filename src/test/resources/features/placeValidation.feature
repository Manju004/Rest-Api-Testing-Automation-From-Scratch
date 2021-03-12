@AddPlace
@sanity
Feature: Validating Place API's

  Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
    Given Add Place payload "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "post" http request
    Then the API call got success with status code 200
    Then "status" in response body is "OK"
    Then "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "GetPlaceAPI"

    Examples:
      | name    | language | address   |
      | A house | English  | 4th cross |
      | B house | English  | 5th cross |
      | C house | spanish  | 6th cross |

  @DeletePlace
  @sanity
  Scenario: Verify if Delete place functionality is working
    Given DeletePlace payload
    When user calls "DeletePlaceAPI" with "POST" http request
    Then the API call got success with status code 200

