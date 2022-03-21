package Gui.model;

import Gui.model.ADT.*;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.statement.IStmt;
import Gui.model.value.StringValue;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Value> heap;
    private int stateID;
    private static int freeID = 1;
    private IStmt originalProgram; //optional field, but good to have
    private ISemaphore semaphoreTable;

    public PrgState(IStack<IStmt> stk, IDictionary<String, Value> symtbl, IList<Value> ot, IDictionary<StringValue, BufferedReader> fT, IHeap<Value> givenHeap, IStmt prg, ISemaphore semaphoreTbl) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fT;
        heap = givenHeap;
        semaphoreTable = semaphoreTbl;
        stateID = getNewPrgStateID();
        originalProgram = prg;
        stk.push(prg);
    }

    public PrgState(IStack<IStmt> stack, MyDictionary<String, Value> stringValueMyDictionary, MyList<Value> valueMyList) {
        exeStack = stack;
        symTable = stringValueMyDictionary;
        out = valueMyList;
    }

    public PrgState(IStmt originalProgram){
        this.exeStack = new MyStack<IStmt>();
        this.symTable = new MyDictionary<String,Value>();
        this.out = new MyList<Value>();
        this.fileTable = new MyDictionary<StringValue,BufferedReader>();
        this.heap = new MyHeap<>();
        this.semaphoreTable = new MySemaphore();
        this.stateID = 1;
        this.exeStack.push(originalProgram);
    }

    public ISemaphore getSemaphoreTable() {
        return semaphoreTable;
    }

    public void setSemaphoreTable(IDictionary<Value, Pair<Value, List<Value>>> semaphoreTbl) {
        this.semaphoreTable.setSemaphore(semaphoreTbl);
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public IStack<IStmt> getStack() {
        return exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<Value> getHeap() {
        return heap;
    }



    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Program state\n");
        str.append("ID: ").append(stateID).append(" \n");
        str.append("Exe Stack: ").append(exeStack).append(" \n");
        str.append("Sym Table: ").append(symTable).append(" \n");
        str.append("Heap: ").append(heap).append(" \n");
        str.append("Output Console: ").append(out).append(" \n");
        str.append("File Table: ").append(fileTable).append(" \n");
        str.append(" \n").append("\n").append("\n");
        return str.toString();
    }

    public void setExeStack(IStack<IStmt> stack) {
        exeStack = stack;
    }

    public void setSymTable(IDictionary<String, Value> table) {
        symTable = table;
    }

    public IList<Value> getOutConsole() {
        return out;
    }

    public void setOutConsole(IList<Value> outConsole) {
        out = outConsole;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }


    public PrgState oneStep() throws MyException, StmtException, ExprException, ADTException {
        if (exeStack.isEmpty()) {
            throw new MyException("prgstate stack is empty");
        }
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public static synchronized int getNewPrgStateID() {
        freeID *= 10;
        return freeID;
    }
}