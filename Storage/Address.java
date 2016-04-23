package Storage;

/**
 * Created by Булат on 21.11.2015.
 */
public class Address {
    private int page;
    private int slot;

    public Address(int page, int slot) {
        this.page = page;
        this.slot = slot;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
