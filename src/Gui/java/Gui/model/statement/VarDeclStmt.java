package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IStack;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.type.Type;
import Gui.model.value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String s, Type deepCopy) {
        name = s;
        type = deepCopy;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException {
        IStack<IStmt> stack = state.getStack();
        IDictionary<String, Value> table = state.getSymTable();
        if (table.isDefined(name)) {
            throw new StmtException("Variable is already declared");
        } else {
//            if (type.equals(new IntType())) {
//                table.add(name, new IntValue());
//            }else if (type.equals(new BoolType())) {
//                table.add(name, new BoolValue());
//            }else if (type.equals(new StringType())) {
//                table.add(name, new StringValue());
//            }  else {
//                throw new StmtException("Type does not exist");
//            }
            table.add(name, type.defaultValue());
        }
        state.setSymTable(table);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(new String(name), type.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.update(name,type);
        return typeEnv;
    }
}