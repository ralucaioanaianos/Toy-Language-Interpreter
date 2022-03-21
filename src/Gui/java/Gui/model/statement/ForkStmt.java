package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.MyStack;
import Gui.model.PrgState;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return new PrgState(new MyStack<>(), state.getSymTable().clone(), state.getOutConsole(), state.getFileTable(),
                state.getHeap(), statement, state.getSemaphoreTable());
    }


    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
