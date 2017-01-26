package ua.com.as.storage;

import ua.com.as.model.containers.ControllerContainer;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.model.containers.ResponseContainer;

import java.util.List;

/**
 * <p>This interface describes class for keeping (save and find) user data wrapped in Containers
 * for better usability.
 */
public interface Storage {
    /**
     * <p>Saves <code>ControllerContainer</code> to storage.
     *
     * @param controllerContainer <code>ControllerContainer</code> to save.
     */
    void save(ControllerContainer controllerContainer);

    /**
     * <p>Finds <code>ResponseContainer</code> in storage by specified <code>RequestContainer</code>.
     *
     * @param requestContainer    <code>RequestContainer</code> to find.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return <code>ResponseContainer</code> object.
     */
    ResponseContainer find(RequestContainer requestContainer, List<String> pathParameterValues);
}
