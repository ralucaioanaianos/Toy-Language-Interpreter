package Gui.model.ADT;

import Gui.model.exceptions.ADTException;
import Gui.model.value.Value;
import javafx.util.Pair;
import java.util.List;

public interface ISemaphore {

    void setSemaphore(IDictionary<Value, Pair<Value, List<Value>>> newSemaphore);
    IDictionary<Value, Pair<Value, List<Value>>> getSemaphore();
    Integer getSemaphoreAddress() throws ADTException;
    void put(Integer foundIndex, Pair<Value, List<Value>> integerListPair) throws ADTException;
}
