package Gui.repository;

import Gui.model.PrgState;
import Gui.model.exceptions.MyException;
import Gui.model.statement.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public List<PrgState> getPrgList();
    PrgState getCrtPrg();
    IStmt getOriginalProgram();
    void addState(PrgState state);
    void printPrgState(PrgState prgState) throws MyException, IOException;
    void setPrgList(List<PrgState> list);

    PrgState getProgramWithId(int id);
}