package az.customers.logger;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.EnumSet;

public class DPLogger {

    private final Logger logger;

    public DPLogger(Logger logger) {
        this.logger = logger;
    }

    public static DPLogger getLogger(Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return new DPLogger(logger);
    }

    Object[] filterValues(Object... args) {
        return Arrays.stream(args).map(a -> a == null ? null : filterValue(a)).toArray();
    }

    private Object filterValue(Object a) {
        return a instanceof Throwable ? a : HtmlUtils.htmlEscape(
                        EnumSet.allOf(FilterPattern.class).stream()
                                .reduce(a,
                                        (o, f) -> o.toString().replaceAll(f.getRegexp(), f.getMask()),
                                        (s1, s2) -> s1.toString() + s2.toString()).toString())
                .replace("/n", "&cr;")
                .replace("/r", "&lf;");
    }

    public String getName() {
        return logger.getName();
    }

    public void trace(String message, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace(message, filterValues(args));
        }
    }

    public void trace(Marker marker, String message, Object... args) {
        if (logger.isTraceEnabled(marker)) {
            logger.trace(marker, message, filterValues(args));
        }
    }

    public void debug(String message, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, filterValues(args));
        }
    }

    public void debug(Marker marker, String message, Object... args) {
        if (logger.isDebugEnabled(marker)) {
            logger.debug(marker, message, filterValues(args));
        }
    }

    public void info(String message, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(message, filterValues(args));
        }
    }

    public void info(Marker marker, String message, Object... args) {
        if (logger.isInfoEnabled(marker)) {
            logger.info(marker, message, filterValues(args));
        }
    }

    public void warn(String message, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(message, filterValues(args));
        }
    }

    public void warn(Marker marker, String message, Object... args) {
        if (logger.isWarnEnabled(marker)) {
            logger.warn(marker, message, filterValues(args));
        }
    }

    public void error(String message, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(message, filterValues(args));
        }
    }

    public void error(Marker marker, String message, Object... args) {
        if (logger.isErrorEnabled(marker)) {
            logger.error(marker, message, filterValues(args));
        }
    }

    @Getter
    private enum FilterPattern {
        NAME_SURNAME("[A-Z]+[ ]+[A-Z]+[ ]+[A-Z]+", "**** ******"),
        MOBILE_NUMBER("\\b(\\+?\\d{1,3}[- ]?)?\\d{9,10}\\b", "**********"),
        FIN("\\b[A-Z\\d]{7}\\b", "*******"),
        EMAIL("\\b([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,5})\\b", "*@*.*"),
        ACCOUNT_NUMBER("^AZ\\d{2}[A-Z]{4}\\d{20}$", "****************************"),
        PASSPORT_SERIAL("^(AZE|AA)\\d{8}$", "*********");

        private final String regexp;
        private final String mask;

        FilterPattern(String regexp, String mask) {
            this.regexp = regexp;
            this.mask = mask;
        }
    }
}
