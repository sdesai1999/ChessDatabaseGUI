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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGui extends Application {

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        ChessDb db = new ChessDb();
        List<ChessGame> list = db.getGames();

        TableView<ChessGame> tview = new TableView<>();

        ObservableList<ChessGame> elements = FXCollections
                                            .observableArrayList(list);

        ListView<ChessGame> listView = new ListView<>(elements);
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
                ChessGame selGame = tview.getSelectionModel().getSelectedItem();
                Stage newStage = new Stage();
                newStage.show();
            });
        ObservableList<ChessGame> selectedItems = tview
                                                 .getSelectionModel()
                                                 .getSelectedItems();
        viewGame.disableProperty().bind(Bindings.isEmpty(selectedItems));

        Button dismiss = new Button("Dismiss");
        dismiss.setOnAction((event) -> System.exit(0));

        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(viewGame, dismiss);
        hbox.setSpacing(5);
        vbox.getChildren().addAll(tview, hbox);
        vbox.setSpacing(5);

        Scene scene = new Scene(vbox);
        stage.setTitle("Chess Database GUI");
        stage.setScene(scene);
        stage.show();
    }
}





















