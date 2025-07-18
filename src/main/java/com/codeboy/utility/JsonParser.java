package com.codeboy.utility;

import java.io.File; 
import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * This JsonParser is a singleton utility class that converts a JSON-File to a JsonNode to 
 * enable search-operations on node-level.
 * 
 * @author MFrikken
 */
public abstract class JsonParser {
    private static final Logger LOGGER = Logger.getLogger(JsonParser.class.getName());

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static JsonNode jsonFileNode;
    private static File currentFile;

    private JsonParser() {}

    /**
     * Parses a given File and caches it as a JsonNode.
     * @param file a pre-read json-file as {@link java.io.File}
     */
    private static void parseFile(File file) {
        if (file.equals(currentFile))
            return;
        try {
            jsonFileNode = objectMapper.readTree(file);
            currentFile = file;
        } catch (IOException e) {
            LOGGER.severe(String.format("[JsonParser] Failed to parse file (%s) to JsonNode", file.getAbsolutePath()));
        }
    }

    public static JsonNode asJsonNodeObject(File file) {
        parseFile(file);
        if (jsonFileNode != null)
            return jsonFileNode;
        throw new RuntimeException(String.format("Given file (%s) could not be parsed to json.", file.getAbsolutePath()));
    }

    /**
     * @param identifier String that identifies a present field in a JsonNode
     * @return a JsonNode-Object for the given identifier with all it's sub-nodes
     */
    public static JsonNode extractNode(String identifier) {  
        JsonNode node = jsonFileNode.get(identifier);
        if (node != null) {
            return node;
        }      
        LOGGER.severe(String.format("[JsonParser] Could not find node with identifier={%s}", identifier));
        return null;
    }

    public static String asJsonString(Object dto) {
        try {
            ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
            return writer.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            LOGGER.severe("[JsonParser] Error while trying to convert object to json-string." + e.getMessage());
            return "";
        }
    }
}
