package nimm.usterkify.data;

import android.content.Context;

import java.util.Optional;

import io.objectbox.BoxStore;

public enum ObjectBoxRepository {

    INSTANCE();

    ObjectBoxRepository() {
    }

    private Optional<BoxStore> boxStore = Optional.empty();

    public static ObjectBoxRepository getInstance() {
        return INSTANCE;
    }

    public BoxStore getBoxStore(Context context) {
        return boxStore.orElseGet(() -> initBoxStore(context));
    }

    private BoxStore initBoxStore(Context context) {
        BoxStore store = MyObjectBox.builder().androidContext(context).build();
        boxStore = Optional.of(store);
        return store;
    }

}
