package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.IntType;
import Gui.model.type.StringType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.StringValue;
import Gui.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private Exp exp;
    private String varName;
    private String fileName;

    public ReadFileStmt(Exp expression, String varName) {
        this.exp = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(varName)) {
            if (symTable.lookup(varName).getType().equals(new IntType())) {
                Value val = exp.eval(symTable, state.getHeap());
                if (val.getType().equals(new StringType())) {
                    StringValue stringVal = (StringValue) val;
                    if (fileTable.isDefined(stringVal)) {
                        BufferedReader bufferedReader = fileTable.lookup(stringVal);
                        try {
                            String line = bufferedReader.readLine();
                            Value intVal;
                            IntType type = new IntType();
                            if (line == null) {
                                intVal = type.defaultValue();
                            } else {
                                intVal = new IntValue(Integer.parseInt(line));
                            }
                            symTable.update(varName, intVal);
                        } catch (IOException e) {
                            throw new StmtException(e.getMessage());
                        }
                    }
                    else {
                        throw new StmtException("The file " + stringVal.getValue() + " is not in the File Table!");
                    }
                }
                else {
                    throw new StmtException("The value couldn't be evaluated to a string value!");
                }
            }
            else {
                throw new StmtException(varName + " is not of type int!");
            }
        }
        else {
            throw new StmtException(varName + " is not defined in Sym Table");
        }

        return null;
    }


    @Override
    public String toString() {
        return "Read From " + exp + " into " + varName;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), new String(varName));
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new IntType()))
            if (typeExp.equals(new StringType())){
                return typeEnv;
            }
            else
                throw new MyException("Read file statement - exp not a string");
        else
            throw new MyException("Read file statement - variable not of type int");
    }
}