import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Tom on 07.06.2016.
 */
public class HexDumpPanel extends Application{
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("HexDump");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Content of the panel
        Text sceneTitle = new Text("What type of input?");
            sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            grid.add(sceneTitle, 0, 0, 2, 1);

        Label inputType = new Label("Type:");
            grid.add(inputType, 0, 1);

        TextField inputTextField = new TextField();
            grid.add(inputTextField, 1, 1);

        Button button = new Button("generate HexDump");
            HBox hbButton = new HBox(10);
            hbButton.setAlignment(Pos.BASELINE_LEFT);
            hbButton.getChildren().add(button);
            grid.add(hbButton, 1, 3);

        final Text actionTarget = new Text();
            grid.add(actionTarget, 1, 5);

        //Events
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionTarget.setFill(Color.CRIMSON);
                actionTarget.setText("work in progress");
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
