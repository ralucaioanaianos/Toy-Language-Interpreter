package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;

public class MulExpression implements Exp {

    Exp exp1;
    Exp exp2;
    // ((exp1*exp2)-(exp1+exp2)).

    public MulExpression(Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1 = exp1.eval(tbl, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.eval(tbl, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) val1;
                IntValue i2 = (IntValue) val2;
                int n1 = i1.getValue();
                int n2 = i2.getValue();
                return new IntValue((n1 * n2) - (n1 + n2));
            }
            else throw new ExprException("second operand is not an integer!");
        }
        else throw new ExprException("first operand is not an integer!");
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = exp1.typecheck(typeEnv), typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType()))
            if (typ2.equals(new IntType()))
                return new IntType();
            else
                throw new MyException("Second operand is not an integer");
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public String toString() {
        return "(" + exp1.toString() + " * " + exp2.toString() + ") - (" + exp1.toString() + " + " + exp2.toString() + ")";
    }

    @Override
    public Exp deepCopy() {
        return new MulExpression(exp1.deepCopy(), exp2.deepCopy());
    }
}
