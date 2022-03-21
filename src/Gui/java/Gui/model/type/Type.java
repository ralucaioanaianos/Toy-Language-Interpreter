package Gui.model.type;

import Gui.model.value.Value;

public interface Type {
    Type deepCopy();
    Value defaultValue();

}