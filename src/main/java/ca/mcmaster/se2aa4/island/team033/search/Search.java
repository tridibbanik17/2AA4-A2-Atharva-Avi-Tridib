package ca.mcmaster.se2aa4.island.team033.search;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.map.Map;

// Defines the contract for search strategies.
public interface Search {
    // Executes the search operation and returns the command.
    String performSearch();

    // Processes the search response and updates the map.
    void readResponse(JSONObject response, Map map);
}
