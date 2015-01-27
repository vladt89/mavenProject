package application;

import org.apache.log4j.Logger;

/**
 * Class which is responsible for file validation.
 *
 * @author vladimir.tikhomirov
 */
public class FileValidator {

    private static final Logger LOG = Logger.getLogger(FileValidator.class);

    public static boolean validate(String pathToFile) {
        LOG.info("Validate file");
        if (pathToFile.endsWith(".csv")) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("File validation was successful - csv file with the path "
                        + pathToFile + " was passed as an input.");
            }
            return true;
        }
        LOG.error("Validation failed. Chosen file is not a csv file");
        return false;
    }
}
