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
import Gui.model.value.Value;

public class SwitchStmt implements IStmt {

    private Exp condition;
    private Exp case1;
    private Exp case2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(Exp condition, Exp case1, Exp case2, IStmt stmt1, IStmt stmt2, IStmt stmt3) {
        this.condition = condition;
        this.case1 = case1;
        this.case2 = case2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> exeStack = state.getStack();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heapTable = state.getHeap();

        Value exp = condition.eval(symTable, heapTable);
        Value exp1 = case1.eval(symTable, heapTable);
        Value exp2 = case2.eval(symTable, heapTable);
        IStmt newStmt;
        if (exp.equals(exp1))
            newStmt = stmt1;
        else if (exp.equals(exp2))
            newStmt = stmt2;
        else
            newStmt = stmt3;
        exeStack.push(newStmt);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return "switch( " + condition.toString() + ") \n(case( " + case1.toString() + " ) " + stmt1.toString() + ")\n(case( " + case2.toString() + " ) " + stmt2.toString() + ")\n(default " + stmt3.toString() + " )";
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(condition.deepCopy(), case1.deepCopy(), case2.deepCopy(), stmt1.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = condition.typecheck(typeEnv);
        //if (typeExp.equals(new BoolType())){
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            stmt3.typecheck(typeEnv);
            return typeEnv.clone();
       // }
       // else
        //    throw new MyException("SWITCH condition is not boolean");
    }
}
