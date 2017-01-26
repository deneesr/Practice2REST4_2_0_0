package ua.com.as.service.conversion;

/**
 * <p>Converts objects from <code>F</code> type to <code>T</code> type.
 *
 * @param <F> initial type of object.
 * @param <T> final type of object.
 */
interface Converter<F, T> {
    /**
     * <p>Converts objects from <code>F</code> type to <code>T</code> type.
     *
     * @param from             initial object with type <code>F</code>.
     * @param infoForException info about controller for Exception.
     * @return object with type <code>T</code>.
     */
    T convert(F from, String infoForException);
}
