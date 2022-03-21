package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.ADT.IStack;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;

public class SleepStmt implements IStmt {

    private Integer number;

    public SleepStmt(Integer number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        if (number != 0) {
            IStack<IStmt> exeStack = state.getStack();
            exeStack.push(new SleepStmt(number - 1));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public String toString() {
        return "sleep( " + number.toString() + " )";
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
