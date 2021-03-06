package micropng.userinterface.inputoptions;

import micropng.commonlib.Status;

public interface ParameterValue<T> extends Cloneable {
    public ValueType getType();

    public T getValue();

    public Status trySetting(T value);
}
