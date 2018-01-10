package com.grubjack.cinema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to execute SQL scripts on a given connection. The script honors SQL comments and
 * separately executes commands one after another.
 */
public class SqlRunner {
    private static Logger log = LoggerFactory.getLogger(DBHelper.class);

    private static final String DEFAULT_DELIMITER = ";";
    private static final Pattern NEW_DELIMITER_PATTERN = Pattern.compile("(?:--|\\/\\/|\\#)?!DELIMITER=(.+)");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^(?:--|\\/\\/|\\#).+");

    public static void runScript(Connection connection, InputStream scriptInputStream) throws SQLException, IOException {
        try (BufferedReader scriptReader = new BufferedReader(new InputStreamReader(scriptInputStream))) {
            StringBuffer command = null;
            String delimiter = DEFAULT_DELIMITER;
            String line;
            while ((line = scriptReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                String trimmedLine = line.trim();
                Matcher delimiterMatcher = NEW_DELIMITER_PATTERN.matcher(trimmedLine);
                Matcher commentMatcher = COMMENT_PATTERN.matcher(trimmedLine);
                if (delimiterMatcher.find()) {
                    delimiter = delimiterMatcher.group(1);
                    log.info("SQL (new delimiter): {}", delimiter);
                } else if (commentMatcher.find()) {
                    log.info("SQL (comment): {}", trimmedLine);
                } else {
                    command.append(trimmedLine);
                    command.append(" ");
                    if (trimmedLine.endsWith(delimiter)) {
                        log.info("SQL: {}", command);
                        Statement statement = connection.createStatement();
                        statement.execute(command.toString());
                        statement.close();
                        command = null;
                    }
                }
            }
        }
    }
}
