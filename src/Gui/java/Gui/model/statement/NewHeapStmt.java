package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.RefType;
import Gui.model.type.Type;
import Gui.model.value.RefValue;
import Gui.model.value.Value;

public class NewHeapStmt implements IStmt{
    String var_name;
    Exp exp;

    public NewHeapStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stack = state.getStack();
        IDictionary<String, Value> symTbl  = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        if (symTbl.isDefined(var_name)){
            if (symTbl.lookup(var_name).getType() instanceof RefType){
                Value val = exp.eval(symTbl, heap);
                Value tblVal = symTbl.lookup(var_name);
                if (val.getType().equals(((RefType)(tblVal.getType())).getInner())){
                    int addr = heap.allocate(val);
                    symTbl.update(var_name, new RefValue(val.getType(), addr));
                } else {
                    throw  new StmtException("Value's type is not correct!");
                }
            } else {
                throw new StmtException("Value's type is not a reference!");
            }
        } else {
            throw new StmtException("Value is not declared!");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewHeapStmt(var_name, exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("New Statement - different types");
    }

    @Override
    public String toString(){
        return "new(" + var_name + ", " + exp + ")";
    }
}
