package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.StringValue;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt{

    private String var;
    private static Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        lock.lock();
        try {
            IDictionary<Value, Pair<Value, List<Value>>> semaphoreTbl = state.getSemaphoreTable().getSemaphore();
            IStack<IStmt> exeStack = state.getStack();

            Value foundIndex = state.getSymTable().lookup(var);

            if (foundIndex == null)
                throw new Exception("no such variable in symtable");
            Pair<Value, List<Value>> semaphoreValue = semaphoreTbl.lookup(foundIndex);
            List<Value> threads = semaphoreValue.getValue();
            Value nMax = semaphoreValue.getKey();
            if (!nMax.equals(new IntValue(threads.size()))) {
                if (threads.contains(new IntValue(state.getStateID())))
                    throw new Exception("already in process");
                threads.add(new IntValue(state.getStateID()));
                state.getSemaphoreTable().getSemaphore().add(foundIndex, new Pair<>(nMax, threads));
            }else {
                exeStack.push(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public String toString() {
        return "aquire( " + var + " )";
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
    }


    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
