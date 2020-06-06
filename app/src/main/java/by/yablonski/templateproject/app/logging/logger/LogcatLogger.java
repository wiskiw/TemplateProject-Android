package by.yablonski.templateproject.app.logging.logger;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.yablonski.templateproject.app.logging.AndroidLogger;
import by.yablonski.templateproject.app.logging.LogEvent;
import by.yablonski.templateproject.app.logging.LogLevel;
import by.yablonski.templateproject.app.Utils;

/**
 * Implementation of AndroidLogger based on Android {@link Log} class.
 *
 * @author Andrey Yablonsky on 31.01.2020
 */
public class LogcatLogger implements AndroidLogger {

    private static final int LOGCAT_TAG_LENGTH_LIMIT = 23;

    private static final String ORG_WORDS_SEPARATOR_REGEXP = "\\.";
    private static final String WORDS_SEPARATOR = ".";
    private static final String START_END_STRING_SEPARATOR_REGEXP = "(^\\.)|(\\.$)";

    @Override
    public void log(LogLevel level, LogEvent event) {

        String fixTag = limitTagLength(event.getName());
        String message = event.getMessage();
        Throwable throwable = event.getThrowable();

        switch (level) {
            case INFO:
                Log.i(fixTag, message, throwable);
                break;

            case DEBUG:
                Log.d(fixTag, message, throwable);
                break;

            case WARN:
                Log.w(fixTag, message, throwable);
                break;

            case ERROR:
                Log.e(fixTag, message, throwable);
                break;
        }
    }

    private static String limitTagLength(String tag) {
        // todo придумать более информативный вариант сжатия тэга

        if (tag.length() > LOGCAT_TAG_LENGTH_LIMIT) {
            return trimFromEnd(tag, LOGCAT_TAG_LENGTH_LIMIT);
        }
        return tag;
    }

//    private static String limitTagLength(String tag) {
//        String fixedTag;
//
//        List<String> words = getWords(tag);
//        if (!words.isEmpty()) {
//            fixedTag = trimWords(words);
//        } else {
//            fixedTag = tag;
//        }
//
//        if (fixedTag.length() > LOGCAT_TAG_LENGTH_LIMIT) {
//            return trimFromEnd(fixedTag, LOGCAT_TAG_LENGTH_LIMIT);
//        } else {
//            return fixedTag;
//        }
//    }

    private static List<String> getWords(String tag) {
        return Arrays.asList(tag.split(ORG_WORDS_SEPARATOR_REGEXP));
    }

    private static String trimWords(List<String> words) {
        if (!words.isEmpty()) {
            List<String> trimmedWords = new ArrayList<>();
            List<String> orgWords = new ArrayList<>(words);

            for (String word : words) {
                trimmedWords.add(stringToFirstLetter(word));
                orgWords.remove(0);

                String fixedTag = concatWords(trimmedWords, orgWords, WORDS_SEPARATOR);

                if (fixedTag.length() <= LOGCAT_TAG_LENGTH_LIMIT) {
                    // если некоторые слова были сокращены, но уже достаточно для прохождения лимита длинны
                    return fixedTag;
                }
            }

            // если все слова были сокращены, но результат все равно превышает лимит
            return concatWords(trimmedWords, orgWords, WORDS_SEPARATOR);

        } else {
            // если список words пуст
            return "";
        }
    }

    private static String stringToFirstLetter(String string) {
        return string.substring(0, Math.min(string.length(), 1));
    }

    private static String trimFromEnd(String string, int len) {
        int length = string.length();
        return string.substring(length - len, length);
    }

    private static String concatWords(List<String> trimmedWords, List<String> otherWords, String separator) {
        String trimmed = Utils.joinToString(trimmedWords, separator);
        String others = Utils.joinToString(otherWords, separator);

        return String.format("%s%s%s", trimmed, separator, others)
            .replaceAll(START_END_STRING_SEPARATOR_REGEXP, "");
    }

}
