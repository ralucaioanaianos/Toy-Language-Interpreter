package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.value.BoolValue;
import Gui.model.value.Value;

public class IfStmt implements IStmt {
    private Exp expression;
    private IStmt thenStatement;
    private IStmt elseStatement;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        expression = e;
        thenStatement = t;
        elseStatement = el;

    }

    @Override
    public String toString() {
        return "if (" + expression + ") then {" + thenStatement + "} else {" + elseStatement + "}";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        Value cond = expression.eval(state.getSymTable(), state.getHeap());
        if (!cond.getType().equals(new BoolType())) {
            throw new StmtException("Condition is not of boolean");
        }
        if (cond.equals(new BoolValue(true))) {
            stack.push(thenStatement.deepCopy());
        } else {
            stack.push(elseStatement.deepCopy());
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            thenStatement.typecheck(typeEnv);
            elseStatement.typecheck(typeEnv);
            return typeEnv.clone();
        }
        else
            throw new MyException("IF condition is not boolean");
    }

}