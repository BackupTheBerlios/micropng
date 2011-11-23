package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public interface ParameterValue<T> {
    public T getValue();

    public Status trySetting(T value);
}
