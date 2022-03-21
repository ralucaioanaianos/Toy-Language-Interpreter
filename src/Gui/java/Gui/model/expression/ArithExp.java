package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide


    public ArithExp(Exp deepCopy, Exp deepCopy1, char op) {
        this.e1 = deepCopy;
        this.e2 = deepCopy1;
        if(op == '+')
            this.op = 1;
        if(op == '-')
            this.op = 2;
        if(op == '*')
            this.op = 3;
        if(op == '/')
            this.op = 4;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1 = e1.eval(tbl, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = e2.eval(tbl, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)val1;
                IntValue i2 = (IntValue)val2;
                int n1 = i1.getValue();
                int n2 = i2.getValue();
                switch (op) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if (n2 == 0) {
                            throw new ExprException("Division by zero");
                        }
                        else {
                            return new IntValue(n1 / n2);
                        }
                    default:
                        throw new ExprException("Incorrect operation");
                }
            }
            else {
                throw new ExprException("Second operand is not an integer");
            }
        }
        else {
            throw new ExprException("First operand is not an integer");
        }

    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv), typ2 = e2.typecheck(typeEnv);
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
        return switch (op) {
            case 1 -> e1.toString() + "+" + e2.toString();
            case 2 -> e1.toString() + "-" + e2.toString();
            case 3 -> e1.toString() + "*" + e2.toString();
            case 4 -> e1.toString() + '/' + e2.toString();
            default -> "";
        };
    }

    @Override
    public Exp deepCopy() {
        return switch (op) {
            case 1 -> new ArithExp(e1.deepCopy(), e2.deepCopy(), '+');
            case 2 -> new ArithExp(e1.deepCopy(), e2.deepCopy(), '-');
            case 3 -> new ArithExp(e1.deepCopy(), e2.deepCopy(), '*');
            case 4 -> new ArithExp(e1.deepCopy(), e2.deepCopy(), '/');
            default -> new ArithExp(e1.deepCopy(), e2.deepCopy(), '+');
        };
    }


}