package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.Type;
import Gui.model.value.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String s, Exp deepCopy) {
        id = s;
        exp = deepCopy;
    }

    @Override
    public String toString(){
        return id + "=" + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IDictionary<String, Value> symTable = state.getSymTable();
        Value value = exp.eval(symTable, state.getHeap());
        if (symTable.isDefined(id)) {
            Type type = (symTable.lookup(id)).getType();
            if (value.getType().equals(type)) {
                symTable.update(id, value);
            }
            else {
                throw new StmtException("Declared type of variable " +
                        id +
                        " and type of the assigned expression do not match");
            }
        }
        else {
            throw new StmtException("The used variable " + id + " was not declared before");
        }
        state.setSymTable(symTable);
        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new AssignStmt(new String(id), exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment Statement: right hand side type != left hand side type");
    }
}