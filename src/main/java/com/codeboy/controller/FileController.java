package com.codeboy.controller;

import java.util.logging.Logger;

import com.codeboy.service.FileService;
import com.codeboy.service.VulnerabilityService;
import com.codeboy.utility.JsonParser;

public class FileController {
    private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());

    private static FileController instance;
    private static FileService fileService;
    private static VulnerabilityService vulnerabilityService;

    private FileController() {
        fileService = new FileService();
        vulnerabilityService = new VulnerabilityService();
    }

    public static FileController getInstance() {
        if (instance == null)
            instance = new FileController();
        return instance;
    }

    public String process(String filePath) {
        if (fileService.processFile(filePath)) {
            return JsonParser.asJsonString(vulnerabilityService.getStatistics());
        }
        LOGGER.severe(String.format("[FileController] Failed to process SAST-Report-File: %s", filePath));
        throw new InternalError("File: " + filePath + " could not be processed\n");
    }

    public void fetchAllVulnerabilities() {
        vulnerabilityService.getAll();
    }

    public String info() {
        return "FileController instance: " + getInstance() + "\nClass: " + FileController.class.getName();
    }
}
