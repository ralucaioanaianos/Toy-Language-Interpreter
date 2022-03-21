package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.*;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.expression.NegateExp;

public class RepeatUntilStmt implements IStmt {

    private IStmt statement;
    private Exp expression;

    public RepeatUntilStmt(IStmt statement, Exp expression) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStmt newStatement = new CompStmt(statement, new WhileStmt(new NegateExp(expression), statement));
        state.getStack().push(newStatement);
        return null;
    }

    @Override
    public String toString() {
        return "repeat{ \n" + statement.toString() + "\n} until " + expression.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type = expression.typecheck(newEnv);
        if (type.equals(new BoolType()))
            return typeEnv;
        else
            throw new MyException("While statement - condition expression is not boolean");

    }
}
