package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.ADT.ISemaphore;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.Type;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt {

    private String variable;
    private Exp expression;
    private static Lock lock = new ReentrantLock();

    public NewSemaphoreStmt(String variable, Exp expression) {
        this.variable = variable;
        this.expression = expression;
    }



    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        lock.lock();
        IDictionary<String, Value> symTbl = state.getSymTable();
        IDictionary<Value, Pair<Value, List<Value>>> semaphoreTbl =state.getSemaphoreTable().getSemaphore();
        IHeap<Value> heapTbl = state.getHeap();
        ISemaphore semaphore = state.getSemaphoreTable();

        Value value = expression.eval(symTbl, heapTbl);
        Integer location = state.getSemaphoreTable().getSemaphoreAddress();
        semaphore.put(location, new Pair<Value, List<Value>>(value, new ArrayList<>()));


        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphoreStmt(variable, expression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newSemaphore( " + variable + ", " + expression.toString() + ")";
    }
}
