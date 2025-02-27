/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.extensions;

import static files.FilesExtended.generateExtensionFilter;
import javax.swing.filechooser.FileFilter;

/**
 * This contains the file extensions and helpful functions for many standard 
 * image formats.
 * @author Milo Steier
 */
public class ImageExtensions {
    /**
     * This class cannot be constructed.
     */
    private ImageExtensions(){}
    /**
     * This has the extension for a JPG file.
     */
    public static final String JPG = "jpg";
    /**
     * This has the extension for a JPEG file.
     */
    public static final String JPEG = "jpeg";
    /**
     * This has the extension for a JPE file.
     */
    public static final String JPE = "jpe";
    /**
     * This has the extension for a JFIF file.
     */
    public static final String JFIF = "jfif";
    /**
     * This is a FileFilter for JPEG files.
     */
    public static final FileFilter JPEG_FILTER = generateExtensionFilter(
            JPEG.toUpperCase(),JPEG,JPG,JPE,JFIF);
    /**
     * This has the extension for a GIF file.
     */
    public static final String GIF = "gif";
    /**
     * This is a FileFilter for GIF files.
     */
    public static final FileFilter GIF_FILTER = generateExtensionFilter(
            GIF.toUpperCase(),GIF);
    /**
     * This has the extension for a TIF file.
     */
    public static final String TIF = "tif";
    /**
     * This has the extension for a TIFF file.
     */
    public static final String TIFF = "tiff";
    /**
     * This is a FileFilter for TIFF files.
     */
    public static final FileFilter TIFF_FILTER = generateExtensionFilter(
            TIFF.toUpperCase(),TIF,TIFF);
    /**
     * This has the extension for a PNG file.
     */
    public static final String PNG = "png";
    /**
     * This is a FileFilter for PNG files.
     */
    public static final FileFilter PNG_FILTER = generateExtensionFilter(
            PNG.toUpperCase(),PNG);
    /**
     * This has the extension for a HEIC file.
     */
    public static final String HEIC = "heic";
    /**
     * This is a FileFilter for HEIC files.
     */
    public static final FileFilter HEIC_FILTER = generateExtensionFilter(
            HEIC.toUpperCase(),HEIC);
    /**
     * This has the extension for a WEBP file.
     */
    public static final String WEBP = "webp";
    /**
     * This is a FileFilter for WEBP files.
     */
    public static final FileFilter WEBP_FILTER = generateExtensionFilter(
            WEBP.toUpperCase(),WEBP);
    /**
     * This is an array of the file extensions for image formats. <br>
     * The positions are as follows: 
     * <ol start="0">
     *  <li>{@link PNG}</li>
     *  <li>{@link JPG}</li>
     *  <li>{@link JPEG}</li>
     *  <li>{@link JPE}</li>
     *  <li>{@link JFIF}</li>
     *  <li>{@link GIF}</li>
     *  <li>{@link TIF}</li>
     *  <li>{@link TIFF}</li>
     *  <li>{@link HEIC}</li>
     *  <li>{@link WEBP}</li>
     * </ol>
     */
    public static final String[] IMAGE_EXT = {PNG,JPG,JPEG,JPE,JFIF,GIF,TIF,
        TIFF,HEIC,WEBP};
    /**
     * This is a FileFilter for image files.
     */
    public static final FileFilter IMAGE_FILTER = generateExtensionFilter(
            "All Picture Files",IMAGE_EXT);
    /**
     * This is an array containing the FileFilters for image files. <br>
     * The positions are as follows: 
     * <ol start="0">
     *  <li>{@link IMAGE_FILTER} - The FileFilter for image files.</li>
     *  <li>{@link PNG_FILTER} - The FileFilter for PNG files.</li>
     *  <li>{@link JPEG_FILTER} - The FileFilter for JPEG files. </li>
     *  <li>{@link GIF_FILTER} - The FileFilter for GIF files. </li>
     *  <li>{@link TIFF_FILTER} - The FileFilter for TIFF files. </li>
     *  <li>{@link HEIC_FILTER} - The FileFilter for HEIC files. </li>
     *  <li>{@link WEBP_FILTER} - The FileFilter for WEBP files. </li>
     * </ol>
     */
    public static final FileFilter[] IMAGE_FILTERS = {
        IMAGE_FILTER,
        PNG_FILTER,
        JPEG_FILTER,
        GIF_FILTER,
        TIFF_FILTER,
        HEIC_FILTER,
        WEBP_FILTER
    };
}
