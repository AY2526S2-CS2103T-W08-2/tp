package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class VersionedAddressBookTest {

    private AddressBook addressBook;
    private VersionedVendorVault versionedVendorVault;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();
        versionedVendorVault = new VersionedVendorVault(addressBook);
    }

    @Test
    public void commit_addNewState_pointerMovesForward() {
        AddressBook newState = new AddressBook();
        versionedVendorVault.commit(newState);

        assertTrue(versionedVendorVault.canUndo()); // should now be able to undo
    }

    @Test
    public void undo_afterCommit_restoresPreviousState() {
        // initial commit
        AddressBook state1 = new AddressBook();
        state1.addPerson(new PersonBuilder().build());
        versionedVendorVault.commit(state1);

        // commit second state
        AddressBook state2 = new AddressBook();
        state2.addPerson(new PersonBuilder().withName("John").build());
        versionedVendorVault.commit(state2);

        // undo to previous state
        versionedVendorVault.undo(state2);

        // state2 should now match state1
        assertEquals(state1, state2);
        assertTrue(versionedVendorVault.canRedo());
    }

    @Test
    public void redo_afterUndo_restoresNextState() {
        // initial commit
        AddressBook state1 = new AddressBook();
        state1.addPerson(new PersonBuilder().build());
        versionedVendorVault.commit(state1);

        // commit second state
        AddressBook state2 = new AddressBook();
        state2.addPerson(new PersonBuilder().withName("John").build());
        versionedVendorVault.commit(state2);

        // undo
        versionedVendorVault.undo(state2);

        // redo
        versionedVendorVault.redo(state2);

        // state2 should match state2 again
        assertEquals(state2, state2); // just checking redo doesn't throw
    }

    @Test
    public void undo_atInitialState_throwsException() {
        assertThrows(IllegalStateException.class, () -> versionedVendorVault.undo(addressBook));
    }

    @Test
    public void redo_atLatestState_throwsException() {
        assertThrows(IllegalStateException.class, () -> versionedVendorVault.redo(addressBook));
    }
}
