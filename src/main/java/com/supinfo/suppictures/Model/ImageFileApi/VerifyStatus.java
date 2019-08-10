package com.supinfo.suppictures.Model.ImageFileApi;

/**
 * Enum on verification of an image file
 */
public enum VerifyStatus {
    /**
     * file has been verified
     */
    VALID,
    /**
     * file was too big
     */
    ERROR_TOO_BIG,
    /**
     * file had an invalid extension
     */
    ERROR_INVALID_EXTENSION
}
