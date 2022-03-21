package Gui.model.statement;

import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.*;
import Gui.model.PrgState;
import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;
import javafx.beans.binding.BooleanExpression;


public class ForStmt implements IStmt {

    private String variable;
    private Exp initialization;
    private Exp condition;
    private Exp increment;
    private IStmt stmt;

    public ForStmt(String variable, Exp initialization, Exp condition, Exp increment, IStmt stmt) {
        this.variable = variable;
        this.initialization = initialization;
        this.condition = condition;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> exeStack = state.getStack();
        IStmt newStatement = new CompStmt(new AssignStmt(variable, initialization), new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "<"),
                new CompStmt(stmt, new AssignStmt(variable, increment))));



                exeStack.push(newStatement);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return "for";
        //return "for( " + variable.toString() + initialization.toString() + "; " + variable.toString() + "<" + condition.toString() + "; " + variable.toString() + "=" + increment.toString() + " )\n" + stmt.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(variable, initialization.deepCopy(), condition.deepCopy(), increment.deepCopy(), stmt.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type = condition.typecheck(newEnv);
        //if (type.equals(new BoolType())) {
            Type typeVar = typeEnv.lookup(variable);
            Type typeExp = initialization.typecheck(typeEnv);
            if (typeVar.equals(typeExp))
                return typeEnv;
            else
                throw new MyException("Assignment Statement: right hand side type != left hand side type");
        //} else
            //throw new MyException("While statement - condition expression is not boolean");
    }
}
