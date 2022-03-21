package Gui.model.util;

import Gui.model.ADT.IStack;
import Gui.model.ADT.MyStack;
import Gui.model.exceptions.ADTException;
import Gui.model.expression.ArithExp;
import Gui.model.value.IntValue;
import Gui.model.value.Value;

public class AddressBuilder {

    private Integer address = 1;
    private static IStack<Integer> freeAddress = new MyStack<>();

    public Integer getFreeAddress() throws ADTException {
        return freeAddress.isEmpty() ? this.address ++ : freeAddress.pop();
    }
}
