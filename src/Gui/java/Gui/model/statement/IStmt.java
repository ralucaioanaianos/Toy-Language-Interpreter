package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;

public interface IStmt {
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException; // execution method for a statement

    IStmt deepCopy();
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException;
}