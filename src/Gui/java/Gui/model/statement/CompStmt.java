package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt deepCopy, IStmt deepCopy1) {
        first = deepCopy;
        snd = deepCopy1;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IStack<IStmt> stack = state.getStack();
        stack.push(snd);
        stack.push(first);
//        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck((first.typecheck(typeEnv)));
    }
}