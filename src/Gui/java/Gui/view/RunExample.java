package Gui.view;

import Gui.controller.Controller;
import Gui.model.ADT.MyDictionary;
import Gui.model.exceptions.MyException;
import Gui.model.type.Type;

import java.io.IOException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.getRepository().getPrgList().get(0).getStack().getValues().get(0).typecheck(new MyDictionary<String, Type>());
            controller.allStep();
        }
        catch (InterruptedException | MyException | IOException e) {
            System.out.println(e.toString());
        }
    }
}
