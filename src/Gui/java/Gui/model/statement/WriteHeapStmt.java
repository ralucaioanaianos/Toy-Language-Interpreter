package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.RefType;
import Gui.model.type.Type;
import Gui.model.value.RefValue;
import Gui.model.value.Value;

public class WriteHeapStmt implements IStmt {
    private String var_name;
    private Exp exp;

    public WriteHeapStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "wH(" + var_name + "," + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        if (state.getSymTable().isDefined(this.var_name)){
            Value val = state.getSymTable().lookup(this.var_name);
            if (val instanceof RefValue){
                int address = ((RefValue)val).getAddress();
                if (state.getHeap().get(address)!=null){ // check if there's anything at that address
                    Value evaluationValue = this.exp.eval(state.getSymTable(),state.getHeap());
                    if (evaluationValue.getType().equals(((RefValue) val).getLocationType())){ //check if the types are equal and update the value at that address in the heap
                        state.getHeap().update(address,evaluationValue);
                    }
                    else
                        throw new StmtException("The pointing variable has a different type than the evaluated expression.");
                }
                else
                    throw new StmtException("The address to which " + var_name + " points is not in the heap");
            }
            else
                throw new StmtException(var_name + " is not a reference variable");
        }
        else
            throw new StmtException(var_name + " is not defined");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(new String(var_name), exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("Heap write statement - different types");
    }

//    @Override
//    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
//        Type typeVar = typeEnv.lookup(var_name);
//        Type typeExp = exp.typecheck(typeEnv);
//        if(typeVar.equals(new ReferenceType(typeExp)))
//            return typeEnv;
//        else
//            throw new MyException("Heap write statement - different types");
//    }
}