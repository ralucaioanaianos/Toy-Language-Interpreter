package Gui.repository;

import Gui.model.PrgState;
import Gui.model.exceptions.MyException;
import Gui.model.statement.IStmt;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> states;
    private IStmt originalProgram;
    private String fileName;
    private int currentIndex;
    private boolean first;


    public Repository(String path) {
        this.states= new ArrayList<>();
        this.currentIndex=0;
        this.fileName = path;
        this.first = true;
    }

    public Repository(PrgState prgState, String fileName) throws IOException, MyException {
        this.originalProgram = prgState.getOriginalProgram();
        states = new LinkedList<>();
        this.fileName = fileName;
        File yourFile = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(yourFile)){
            fileWriter.write("");
        } catch (IOException e){
            throw  new MyException(e.getMessage());
        }
    }

    public Repository() {
        states = new LinkedList<>();
    }

    @Override
    public List<PrgState> getPrgList() {
        return states;
    }


    @Override
    public PrgState getCrtPrg() {
        PrgState state = states.get(0);
        states.remove(0);
        return state;
    }

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }


    @Override
    public void addState(PrgState state) {
        states.add(state);
    }

    @Override
    public void printPrgState(PrgState progState) throws MyException, IOException {
        PrintWriter writer;
        if (first)
        {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.fileName,false)));
            first = false;
        }
        else
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.fileName,true)));
        writer.print(progState);
        writer.close();
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.states = list;
    }

    @Override
    public PrgState getProgramWithId(int id) {
        for (PrgState p: states)
            if(p.getStateID()==id)
                return p;
        return null;
    }
}