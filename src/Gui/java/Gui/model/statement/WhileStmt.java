package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.value.BoolValue;
import Gui.model.value.Value;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt statement;

    public WhileStmt(Exp exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stk = state.getStack();
        IDictionary<String, Value> symTable = state.getSymTable();
        Value val = exp.eval(symTable, state.getHeap());
        if (val.getType().equals(new BoolType())){
            BoolValue boolValue = (BoolValue) val;
            if (boolValue.getValue()){
//                stk.push(this.deepCopy());
//                stk.push(statement);
                state.getStack().push(new WhileStmt(exp, statement));
                state.getStack().push(statement);
            }
        } else {
            throw new StmtException("While condition is not boolean!");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), statement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type = exp.typecheck(newEnv);
        if (type.equals(new BoolType()))
            return typeEnv;
        else
            throw new MyException("While statement - condition expression is not boolean");
    }

    @Override
    public String toString() {
        return "while (" + exp + ") {" + statement + "}";
    }
}
