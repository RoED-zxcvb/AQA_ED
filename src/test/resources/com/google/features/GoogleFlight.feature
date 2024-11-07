Feature: someFeatureName
  Scenario Outline: someScenarioNameForPassTest
    Given Google flight page open
    When I enter arrival airport "<arrivalAirport>"
    When I select arrival airport by index 0
    When I enter departure airport "<departureAirport>"
    When I select departure airport by index 0
    When I select the trip type "One way"
    When I open departure calendar
    When I select departure date in calendar by number of available day <departureDay>
    When I click calendar button Done
    When I click button Search
    When I open stops filter
    When I select the stops filter "Nonstop only"
    When I close filter list
    Then I check all arrival airports IATA of flights equals "<arrivalAirport>"
    Then I check all departure airports IATA of flights equals "<departureAirport>"

    Examples:
      | departureAirport | arrivalAirport | departureDay|
      | IST              | ADB            | 2           |
      | LIS              | FNC            | 3           |

