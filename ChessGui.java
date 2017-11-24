import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGui extends Application {

    private ChessDb db = new ChessDb();
    private TableView<ChessGame> tview = new TableView<>();
    private List<ChessGame> list = db.getGames();
    private ObservableList<ChessGame> elements = FXCollections
                                                .observableArrayList(list);
    private TextField searchField = new TextField();

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        // ChessDb db = new ChessDb();
        // List<ChessGame> list = db.getGames();

        // TableView<ChessGame> tview = new TableView<>();

        // ObservableList<ChessGame> elements = FXCollections
        //                                     .observableArrayList(list);
        tview.setItems(elements);

        TableColumn eventCol = new TableColumn("Event");
        eventCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("event"));
        TableColumn siteCol = new TableColumn("Site");
        siteCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("site"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("date"));
        TableColumn whiteCol = new TableColumn("White");
        whiteCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("white"));
        TableColumn blackCol = new TableColumn("Black");
        blackCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("black"));
        TableColumn resultCol = new TableColumn("Result");
        resultCol.setCellValueFactory(
            new PropertyValueFactory<ChessGame, String>("result"));
        tview.getColumns().addAll(eventCol, siteCol, dateCol, whiteCol,
            blackCol, resultCol);

        Button viewGame = new Button("View Game");
        viewGame.setOnAction((event) -> {
                Stage newStage = new Stage();

                ChessGame selGame = tview.getSelectionModel().getSelectedItem();
                List<String> metadata = new ArrayList<>();
                metadata.add("Event: " + selGame.getEvent());
                metadata.add("Site: " + selGame.getSite());
                metadata.add("Date: " + selGame.getDate());
                metadata.add("White: " + selGame.getWhite());
                metadata.add("Black: " + selGame.getBlack());
                metadata.add("Result: " + selGame.getResult());

                ListView<String> lView = new ListView<>();
                lView.getItems().addAll(metadata);

                Button dismiss1 = new Button("Dismiss");
                dismiss1.setOnAction((newEvent) -> newStage.close());

                VBox vbox1 = new VBox();
                vbox1.getChildren().addAll(lView, dismiss1);
                vbox1.setSpacing(5);
                Scene scene1 = new Scene(vbox1);

                newStage.setScene(scene1);
                newStage.show();
            });
        ObservableList<ChessGame> selectedItems = tview
                                                 .getSelectionModel()
                                                 .getSelectedItems();
        viewGame.disableProperty().bind(Bindings.isEmpty(selectedItems));

        Button dismiss = new Button("Dismiss");
        dismiss.setOnAction((event) -> System.exit(0));

        searchField.setPromptText("Filter");
        searchField.textProperty().addListener((obs, oldText, newText) -> {
                searchFilter(newText);
            });

        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(viewGame, dismiss, searchField);
        hbox.setSpacing(5);
        vbox.getChildren().addAll(tview, hbox);
        vbox.setSpacing(5);

        Scene scene = new Scene(vbox);
        stage.setTitle("Chess Database GUI");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    private void searchFilter(String text) {
        if (searchField.textProperty().get().isEmpty()) {
            tview.setItems(elements);
            return;
        }

        ObservableList<ChessGame> newTableRows = FXCollections
                                                .observableArrayList();
        ObservableList<TableColumn<ChessGame, ?>> tableCols = tview
                                                             .getColumns();
        for (int i = 0; i < elements.size(); i++) {
            boolean toContinue = true;
            for (int j = 0; j < tableCols.size(); j++) {
                TableColumn testCol = tableCols.get(j);
                String currCellText = testCol
                                     .getCellData(elements.get(i))
                                     .toString()
                                     .toLowerCase();
                if (currCellText.contains(text.toLowerCase()) && toContinue) {
                    newTableRows.add(elements.get(i));
                    toContinue = false;
                }
            }
        }

        tview.setItems(newTableRows);
    }
}