package micropng.userinterface;

import micropng.commonlib.Status;

public interface InputConfigurationOption<T extends InputConfigurationData> {

    public Status add(T t);
    public Status validate();
}
