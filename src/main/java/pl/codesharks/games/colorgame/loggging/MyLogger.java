package pl.codesharks.games.colorgame.loggging;

import java.io.IOException;
import java.util.logging.*;

@SuppressWarnings("FieldCanBeLocal")
public class MyLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");

        Handler[] handlers = rootLogger.getHandlers();

        if (handlers[0] instanceof ConsoleHandler) {

            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);

        fileTxt = new FileHandler("log.txt",true);
        fileHTML = new FileHandler("log.html");


        formatterTxt = new SimpleFormatter();

        fileTxt.setFormatter(formatterTxt);

        logger.addHandler(fileTxt);


        formatterHTML = new MyHtmlFormatter();

        fileHTML.setFormatter(formatterHTML);

        logger.addHandler(fileHTML);

    }
    public static void close(){
        fileHTML.flush();
        fileHTML.close();

        fileTxt.flush();
        fileTxt.close();
    }

}


