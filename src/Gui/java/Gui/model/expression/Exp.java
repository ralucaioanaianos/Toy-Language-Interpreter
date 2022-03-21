package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.Type;
import Gui.model.value.Value;

public interface Exp {
    public Value eval(IDictionary<String,Value> tbl, IHeap<Value> heap) throws ExprException;
    Type typecheck(IDictionary<String, Type> typeEnv) throws MyException;
    Exp deepCopy();
}