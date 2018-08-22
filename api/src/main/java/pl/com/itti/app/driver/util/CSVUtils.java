package pl.com.itti.app.driver.util;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer writer, List<String> values) throws IOException {
        writeLine(writer, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separators) throws IOException {
        writeLine(writer, values, separators, ' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separators, char customQuote) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        for (String value : values) {
            if (!first) {
                stringBuilder.append(separators);
            }

            if (customQuote == ' ') {
                stringBuilder.append(followCVSformat(value));
            } else {
                stringBuilder.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }

        stringBuilder.append("\n");
        writer.append(stringBuilder.toString());
    }

    private static String followCVSformat(String value) {
        String result = value;

        if (Strings.isNullOrEmpty(result)) {
            result = "";
        } else if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }

        return result;
    }
}
