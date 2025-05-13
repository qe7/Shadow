package me.shae.shadow.utils.config;

import me.shae.shadow.Shadow;

import java.io.File;
import java.nio.file.Files;

/**
 * Utility class for file operations related to Adidas's configuration.
 * Handles creation, reading, writing, and deletion of configuration files.
 */
public final class FileUtil {

    // File extension used for all configuration files
    private static final String FILE_EXTENSION = ".json";

    // Base directory path for storing configuration files
    private static final String FILE_DIRECTORY = System.getProperty("user.home") + File.separator + "shadow" + File.separator;

    private FileUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Creates the configuration directory if it doesn't exist.
     * Logs success/failure messages through Adidas's logger.
     */
    public static void createDirectory() {
        File directory = new File(FILE_DIRECTORY);

        if (!directory.exists()) {
            try {
                if (directory.mkdirs()) {

                }
            } catch (Exception e) {

            }
        } else {

        }
    }

    /**
     * Creates a new configuration file.
     *
     * @param fileName Name of the file to create (without extension)
     */
    public static void createFile(final String fileName) {
        File file = new File(FILE_DIRECTORY + fileName + FILE_EXTENSION);

        createDirectory();

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {

                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Deletes a configuration file.
     *
     * @param fileName Name of the file to delete (without extension)
     */
    public static void deleteFile(final String fileName) {
        File file = new File(FILE_DIRECTORY + fileName + FILE_EXTENSION);

        if (file.exists()) {
            try {
                if (file.delete()) {

                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Reads the contents of a configuration file.
     *
     * @param fileName Name of the file to read (without extension)
     * @return File contents as String, or null if file doesn't exist or can't be read
     */
    public static String readFile(final String fileName) {
        File file = new File(FILE_DIRECTORY + fileName + FILE_EXTENSION);

        if (file.exists()) {
            try {
                return new String(Files.readAllBytes(file.toPath()));
            } catch (Exception e) {

            }
        }

        return null;
    }

    /**
     * Writes content to a configuration file.
     * Creates the file if it doesn't exist.
     *
     * @param fileName Name of the file to write to (without extension)
     * @param content  Content to write to the file
     */
    public static void writeFile(final String fileName, final String content) {
        File file = new File(FILE_DIRECTORY + fileName + FILE_EXTENSION);

        createDirectory();

        if (!file.exists()) {
            createFile(fileName);
        }

        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (Exception e) {

        }
    }
}