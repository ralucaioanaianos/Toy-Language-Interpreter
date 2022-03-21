package Gui.model.value;

import Gui.model.type.Type;

public interface Value {
    Type getType();

    Value deepCopy();
}