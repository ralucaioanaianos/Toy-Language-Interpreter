package Gui.model.value;

import Gui.model.type.IntType;
import Gui.model.type.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        val = v;
    }

    public IntValue() {
        val  = 0;
    }

    public int getValue() {
        return val;
    }

    public int increase() {
        return val + 1;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue))
            return false;
        IntValue t = (IntValue) o;
        return t.val == val;
        //return val.equals(t.val);
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}