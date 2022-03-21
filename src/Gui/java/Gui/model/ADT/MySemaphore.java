package Gui.model.ADT;

import Gui.model.exceptions.ADTException;
import Gui.model.util.AddressBuilder;
import Gui.model.value.IntValue;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.util.List;

public class MySemaphore implements ISemaphore {

    private IDictionary<Value, Pair<Value, List<Value>>> semaphore;
    private AddressBuilder semaphoreAddress = new AddressBuilder();

    public MySemaphore() {
        semaphore = new MyDictionary<>();
    }

    public IDictionary<Value, Pair<Value, List<Value>>> getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(IDictionary<Value, Pair<Value, List<Value>>> newSemaphore) {
        this.semaphore = newSemaphore;
    }

    public Integer getSemaphoreAddress() throws ADTException {
        return semaphoreAddress.getFreeAddress();
    }

    //@Override
    public void put(Integer foundIndex, Pair<Value, List<Value>> integerListPair) throws ADTException {
        Value fI = new IntValue(foundIndex);
        semaphore.add(fI, integerListPair);
    }

}
