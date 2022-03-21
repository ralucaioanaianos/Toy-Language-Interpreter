package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.BoolType;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.BoolValue;
import Gui.model.value.Value;

public class NegateExp implements Exp {

    private Exp expression;

    public NegateExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        //return expression.eval(tbl, heap) == 0 ? 1 : 0;

        if (expression.eval(tbl, heap).equals(new BoolValue(false)))
            return new BoolValue(true);
        else return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "!" + expression.toString();
    }

    @Override
    public Exp deepCopy() {
        return new NegateExp(expression.deepCopy());
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return new BoolType();
        else
            throw new MyException("Second operand is not an integer");

    }
}
