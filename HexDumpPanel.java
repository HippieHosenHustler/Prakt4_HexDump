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
public class HexDumpPanel extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Parameters parameters = getParameters();

        primaryStage.setTitle("HexDump");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Content of the panel
        Text sceneTitle = new Text("What type of input?");
        sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label inputType = new Label("Type:");
        grid.add(inputType, 0, 1);

        TextField inputTextField = new TextField();
        grid.add(inputTextField, 1, 1);

        Button button = new Button("generate Hexdump");
        HBox hbButton = new HBox(0);
        hbButton.setAlignment(Pos.BASELINE_CENTER);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 2, 2, 1);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 3, 2, 1);

        //Events
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionTarget.setFill(Color.CRIMSON);
                actionTarget.setText("something");

            }
        });

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    byte data[];

    private char[] hexByte(int z, int len) {
        char[] x = new char[len];
        int i, hx;
        for (i = len - 1; i >= 0; i--) {
            hx = z;
            z >>>= 4;
            hx &= 0xf;
            x[i] = (char) (hx <= 9 ? hx + '0' : hx + 'A' - 10);
        }
        return x;
    }

    public void getHexString() {
        int i, j, line = 0;
        String s = new String(hexByte(line, 4)) + ": ";
        String temp;
        String plainText;

        String dataString = new String(data);

        for (i = 0; i < dataString.length(); i++) {
            temp = new String(hexByte(data[i], 2));
            s = s + temp;

            if ((i + 1) % 16 == 0 && (i + 1) != dataString.length()) {
                line++;
                temp = new String(hexByte(line, 4));
                plainText = "";

                for (j = (i - 15); j <= i; j++) {
                    plainText = plainText + new String(hexByte(data[j], 1));
                }

                s = s + "\t\t" + plainText + "\n" + temp + ": ";
            } else if ((i + 1) % 4 == 0 && (i + 1) != dataString.length()) {
                s = s + " | ";
            } else {
                s = s + " ";
            }
        }
    }
}
