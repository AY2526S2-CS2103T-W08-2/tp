package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.UndoCommand;

/**
 * Represents the version history of whole of VendorVault.
 */
public class VersionedVendorVault {

    private final List<AddressBook> addressBookStateList;
    // private final List<Inventory> inventoryStateList;
    private int currentStatePointer;

    /**
     * Creates a VersionedVendorVault with the given initial state.
     *
     * @param initial Initial state of the VendorVault.
     */
    public VersionedVendorVault(ReadOnlyAddressBook initial) {
        this.addressBookStateList = new ArrayList<>();
        this.addressBookStateList.add(new AddressBook(initial));
        this.currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current VendorVault state to the history.
     */
    public void commit(AddressBook currentState) {
        // add parameter Inventory currentStateInventory when Inventory is implemented
        // state will be stored as a whole VendorVault state, so that undo and redo can be done in one step
        // which is what user expects when they undo or redo a command
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();

        addressBookStateList.add(new AddressBook(currentState));

        currentStatePointer++;
    }

    /**
     * Restores the previous VendorVault state.
     */
    public void undo(AddressBook currentState) {
        if (!canUndo()) {
            throw new IllegalStateException(UndoCommand.MESSAGE_FAILURE);
        }
        currentStatePointer--;
        currentState.resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the next VendorVault state after an undo.
     */
    public void redo(AddressBook currentState) {
        if (!canRedo()) {
            // TODO: Replace this when RedoCommand is implemented
            throw new IllegalStateException(UndoCommand.MESSAGE_FAILURE);
        }
        currentStatePointer++;
        currentState.resetData(addressBookStateList.get(currentStatePointer));
    }

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }
}
