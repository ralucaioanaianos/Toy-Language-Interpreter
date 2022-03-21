package Gui.model.statement;

import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.ADT.*;
import Gui.model.type.BoolType;
import Gui.model.type.Type;

public class ConditionalAssignmentStmt implements IStmt {

    private String variable;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public ConditionalAssignmentStmt(String variable, Exp exp1, Exp exp2, Exp exp3) {
        this.variable = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> exeStack = state.getStack();
        IStmt newStatement = new IfStmt(exp1, new AssignStmt(variable, exp2), new AssignStmt(variable, exp3));
        exeStack.push(newStatement);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return variable + " = " + exp1.toString() + " ? " + exp2.toString() + " : " + exp3.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(variable, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp1 = exp1.typecheck(typeEnv);
        Type typeExp2 = exp2.typecheck(typeEnv);
        Type typeExp3 = exp3.typecheck(typeEnv);
        if (typeExp1.equals(new BoolType()))
            return typeEnv.clone();
        else
            throw new MyException("condition is not boolean!");
    }
}
