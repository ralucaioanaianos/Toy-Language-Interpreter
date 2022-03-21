package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}