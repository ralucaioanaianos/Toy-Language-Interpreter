package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.StringType;
import Gui.model.type.Type;
import Gui.model.value.StringValue;
import Gui.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private Exp exp;

    public CloseRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        Value val = exp.eval(symTable, state.getHeap());
        if (val.getType().equals(new StringType())) {
            IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringVal = (StringValue) val;

            if (fileTable.isDefined(stringVal)){
                BufferedReader bufferedReader = fileTable.lookup(stringVal);
                try {
                    bufferedReader.close();
                } catch (IOException e){
                    throw new StmtException(e.getMessage());
                }
                fileTable.remove(stringVal);

            } else {
                throw new StmtException("The file doesn't exist in the File Table!");
            }
        } else {
            throw new StmtException("Expression could not be evaluated to a string in File Close!");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type ExpressionType = exp.typecheck(typeEnv);
        if (ExpressionType.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new MyException("Close Statement -  expression type is not a string");
    }

    @Override
    public String toString(){
        return "close(" + exp + ")";
    }
}
