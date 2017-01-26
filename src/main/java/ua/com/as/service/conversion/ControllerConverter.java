package ua.com.as.service.conversion;

import ua.com.as.model.containers.ControllerContainer;
import ua.com.as.model.containers.QueryContainer;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.model.jaxb.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Converts objects from <code>Controller</code> type to <code>ControllerContainer</code> type.
 */
class ControllerConverter implements Converter<Controller, ControllerContainer> {

    /**
     * <p>Converts objects from <code>Controller</code> type to <code>ControllerContainer</code> type.
     *
     * @param from             initial object with type <code>Controller</code>.
     * @param infoForExcep info about controller for Exception.
     * @return object with type <code>ControllerContainer</code>.
     */
    @Override
    public ControllerContainer convert(Controller from, String infoForExcep) {
        Converter<Query, QueryContainer> converter = new QueryConverter();

        String fullClassName = from.getFullClassName();
        String infoForException = fullClassName;

        List<Query> queries = from.getQueries();
        List<QueryContainer> queryContainers = new ArrayList<>();
        queries.forEach(query -> queryContainers.add(converter.convert(query, infoForException)));

        return new ControllerContainer(fullClassName, queryContainers);
    }
}
