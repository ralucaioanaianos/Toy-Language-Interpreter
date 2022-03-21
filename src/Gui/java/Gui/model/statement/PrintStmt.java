package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IList;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.Type;
import Gui.model.value.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp deepCopy) {
        exp = deepCopy;
    }

    @Override
    public String toString(){
        return "print(" + exp.toString() +")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        IList<Value> outConsole = state.getOutConsole();
        outConsole.add(exp.eval(state.getSymTable(), state.getHeap()));
        state.setExeStack(stack);
        state.setOutConsole(outConsole);
//        return state;
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

}