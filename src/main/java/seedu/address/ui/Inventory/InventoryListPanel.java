package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InventoryListPanel extends UiPart<Region> {

    private static final String FXML = "InventoryListPanel.fxml";
    private static final int LOW_STOCK_THRESHOLD = 10;

    @FXML
    private ListView<String> inventoryListView;

    public InventoryListPanel() {
        super(FXML);

        ObservableList<String> fakeInventory = FXCollections.observableArrayList(
                "Product A:001:25",
                "Product B:002:18",
                "Product C:003:7",
                "Product D:004:3"
        );

        SortedList<String> sortedInventory = new SortedList<>(fakeInventory, (a, b) -> {

            String[] partsA = a.split(":");
            String[] partsB = b.split(":");

            int idA = Integer.parseInt(partsA[1]);
            int idB = Integer.parseInt(partsB[1]);

            int qtyA = Integer.parseInt(partsA[2]);
            int qtyB = Integer.parseInt(partsB[2]);

            boolean lowA = qtyA <= LOW_STOCK_THRESHOLD;
            boolean lowB = qtyB <= LOW_STOCK_THRESHOLD;

            if (lowA && !lowB) return -1;
            if (!lowA && lowB) return 1;

            if (lowA && lowB) {
                return Integer.compare(qtyA, qtyB);
            }

            return Integer.compare(idA, idB);
        });

        inventoryListView.setItems(sortedInventory);

        inventoryListView.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                String[] parts = item.split(":");
                String name = parts[0];
                String id = parts[1];
                int qty = Integer.parseInt(parts[2]);

                Label nameLabel = new Label((getIndex() + 1) + ". " + name);
                nameLabel.setTextFill(Color.WHITE);
                nameLabel.setStyle("-fx-font-size: 15px;");

                Label idLabel = new Label("ID: " + id);
                idLabel.setTextFill(Color.LIGHTGRAY);

                Label qtyLabel = new Label(String.valueOf(qty));

                if (qty > LOW_STOCK_THRESHOLD) {
                    qtyLabel.setStyle(
                            "-fx-background-color: #2ecc71;" +
                            "-fx-text-fill: black;" +
                            "-fx-padding: 3 8 3 8;" +
                            "-fx-background-radius: 10;"
                    );
                } else {
                    qtyLabel.setStyle(
                            "-fx-background-color: #e74c3c;" +
                            "-fx-text-fill: white;" +
                            "-fx-padding: 3 8 3 8;" +
                            "-fx-background-radius: 10;"
                    );
                }

                VBox card = new VBox(nameLabel, idLabel, qtyLabel);
                card.setSpacing(6);

                String backgroundColor;
                if (getIndex() % 2 == 0) {
                    backgroundColor = "#313744";
                } else {
                    backgroundColor = "#3c4150";
                }

                card.setStyle(
                        "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-padding: 12;"
                );

                setStyle("-fx-background-color: transparent;");
                setGraphic(card);
                setText(null);
            }
        });
    }
}
