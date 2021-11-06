Feature: Weather Station


 
  Scenario: Validate that attempt to register a weather station without an API key
    When I try to register weather station without APIKey in request
    Then I expect the response with status code <statusCode> and <errorMessage>
     Examples: 
      | statusCode  | errorMessage |
      | 401         | "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."|

 
  Scenario Outline: Successfully register two stations
    When I try to register weather station with valid APIKey and values <external_id>,<name>,<latitude>,<longitude>,<altitude>
    Then I expect the response with status code <statusCode>

    Examples: 
      | external_id  		 | name 												| latitude | longitude | altitude | statusCode |
      | "DEMO_TEST001"	 | "Team Demo Test Station 001" | 33.33 	 | -122.43 	 | 222      | 201        |
      | "DEMO_TEST002"   | "Team Demo Test Station 002" | 44.44    | -122.44   | 111      | 201        |
      
  Scenario Outline: Verify that the stations were successfully stored in the DB and values same as in register request
  	Given I register a weather staion with valid APIKey and values <external_id>,<name>,<latitude>,<longitude>,<altitude>
    When I try to get registered weather station
    Then I expect the response with status code <statusCode> and values <external_id>,<name>,<latitude>,<longitude>,<altitude>

    Examples: 
      | statusCode | external_id  		 | name 												| latitude | longitude | altitude |
      | 200        | "DEMO_TEST003"	   | "Team Demo Test Station 003" | 55.55 	 | -122.45 	 | 333      |