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

import java.io.*;
import java.util.List;

/**
 * Created by Tom on 07.06.2016.
 */
public class HexDumpPanel extends Application {
    public static void main(String[] args) {

        //create new URLLoad object, pass the parameters and set the content of the textfile from args[2]
        URLLoad urlObject = new URLLoad();
        urlObject.setFileText(args[0], args[1]);

        //create new URLConnectionLoad object, pass parameters and set the content of the textfile from args[2]
        URLConnectionLoad urlsConnectionObject = new URLConnectionLoad();
        urlsConnectionObject.setFileText(args[0], args[1]);

        Application.launch(args);
    }

    byte data[];

    @Override
    public void start(Stage primaryStage) {

        //pass parameters to JavaFx Application via "parameterArray"
        Parameters parameters = getParameters();
        List<String> rawParameters = parameters.getRaw();
        String [] parameterArray = rawParameters.toArray(new String[rawParameters.size()]);

        if(parameterArray.length == 1){
            //pass bytes from parameters to data if 1 parameter is present
            data = parameterArray[0].getBytes();
        }
        else if(parameterArray.length == 2){
            //pass bytes from file to data if exactly 2 parameters are present with 0 being the URL and 1 being the destination file
            try
            {
                InputStream inputFile = new FileInputStream(parameterArray[1]);
                ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
                byte buf[]=new byte[1024];
                int lenr;

                while ((lenr=inputFile.read(buf))>-1) bos.write(buf,0,lenr);

                data=bos.toByteArray();
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        else{
            System.err.println("Incorrect parameter count!\n\n1 for direct parameter to HexDump\n2 for URL to HexDump");
            System.exit(1);
        }

        //set the stage
        primaryStage.setTitle("HexDump");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //content of the panel
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
        grid.add(actionTarget, 0, 3, 3, 1);

        //create hexString
        int i, j, line = 0;
        String s = new String(hexByte(line, 4)) + ":\t";
        String temp;
        String dataString = new String(data);

        for (i = 0; i < dataString.length(); i++) {
            temp = new String(hexByte(data[i], 2));
            s = s + temp;

            if ((i + 1) % 16 == 0 && (i + 1) != dataString.length()) {
                line++;
                temp = new String(hexByte(line, 4));
                s = s + "\n" + temp + ":\t";
            } else if ((i + 1) % 4 == 0 && (i + 1) != dataString.length()) {
                s = s + "\t  |\t";
            } else {
                s = s + "\t";
            }
        }

        //set final variable for inner method below
        final String hexDumpString = s;

        //events
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionTarget.setFill(Color.CRIMSON);
                actionTarget.setText(hexDumpString);
            }
        });

        Scene scene = new Scene(grid, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
}
