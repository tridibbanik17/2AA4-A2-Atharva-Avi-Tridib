package ca.mcmaster.se2aa4.island.team033.search;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.map.Map;

// The Search interface defines the common behavior for different types of search strategies.
public interface Search {
    // Method to perform a search action. The implementation will vary depending on the specific search strategy.
    String performSearch();
    // Method to process the response after performing a search. This method will update the map with new information received.
    void readResponse(JSONObject response, Map map);
} 