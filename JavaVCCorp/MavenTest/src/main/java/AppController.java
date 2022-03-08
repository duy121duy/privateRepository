import org.apache.log4j.Logger;

public class AppController {
    private static final Logger logger = Logger.getLogger(AppController.class);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            logger.debug("debug log");
            logger.error("error log");
            logger.info("info log");
        }
    }
}
