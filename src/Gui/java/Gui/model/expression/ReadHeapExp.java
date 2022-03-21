package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.RefType;
import Gui.model.type.Type;
import Gui.model.value.RefValue;
import Gui.model.value.Value;

public class ReadHeapExp implements Exp{
    private Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val = exp.eval(tbl, heap);
        if (val instanceof RefValue) {
            int refVal = ((RefValue) val).getAddress();
            Value valueFromHeap = heap.get(refVal);
            if (valueFromHeap != null) {
                return valueFromHeap;
            } else {
                throw new ExprException("The address doesn't exist in the heap");
            }

        } else {
            throw new ExprException("The expression could not be evaluated to a RefValue");
        }
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType){
            return ((RefType)typ).getInner();
        }
        else
            throw new MyException("Read Heap argument not Ref Type.");
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
