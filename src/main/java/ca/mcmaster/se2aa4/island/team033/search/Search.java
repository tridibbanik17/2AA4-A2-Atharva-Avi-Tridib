package ca.mcmaster.se2aa4.island.team033.search;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.map.Map;

/**
 * The Search interface defines the common behavior for different types of search strategies.
 */
public interface Search {
    /**
     * Performs a search action. The implementation varies based on the specific search strategy.
     * @return The command to execute the search.
     */
    String performSearch();

    /**
     * Processes the response after performing a search and updates the map with new information.
     * @param response The JSON response from the search operation.
     * @param map The map to update with new points of interest.
     */
    void readResponse(JSONObject response, Map map);
}

