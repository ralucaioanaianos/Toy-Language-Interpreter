package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.value.BoolValue;
import Gui.model.value.Value;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op;

    public LogicExp(Exp deepCopy, Exp deepCopy1, int op1) {
        e1 = deepCopy;
        e2 = deepCopy1;
        op = op1;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1 = e1.eval(tbl, heap);
        if (val1.getType().equals(new BoolType())) {
            val2 = e2.eval(tbl, heap);
            if (val2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)val1;
                BoolValue i2 = (BoolValue)val2;
                boolean x = i1.getValue();
                boolean y = i2.getValue();
                if (op == 1) {
                    return new BoolValue(x && y);
                }
                else if (op == 2) {
                    return new BoolValue(x || y);
                }
            }
            else {
                throw new ExprException("Second operand is not a boolean");
            }
        }
        else {
            throw new ExprException("First operand is not a boolean");
        }

        return new BoolValue(false);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv), typ2 = e2.typecheck(typeEnv);
        if (e1.equals(new BoolType()))
            if (e2.equals(new BoolType()))
                return new BoolType();
            else
                throw new MyException("Second operand is not an integer");
        else
            throw new MyException("Second operand is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }
}