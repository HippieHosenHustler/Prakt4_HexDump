import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        Application.launch(args);
        //test-url: http://www.informatik.htw-dresden.de/~beck/PSPII_WI/praktika/java08.html
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
            char[] plainText = new char[parameterArray[0].length()];
        }
        else if(parameterArray.length == 2 || parameterArray.length == 3){
            //pass bytes from file to data if exactly 2 parameters are present with 0 being the URL and 1 being the destination file
            try
            {
                InputStream inputFile = new FileInputStream(parameterArray[1]);
                ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
                byte buf[]=new byte[1024];
                int lenr;

                while ((lenr=inputFile.read(buf))>-1) bos.write(buf,0,lenr);

                data=bos.toByteArray();

                BufferedReader reader = new BufferedReader(new FileReader(parameterArray[1]));
                //char[] plainText = new char[length of string from file goes here];
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        else{
            System.err.println("Incorrect parameter count!\n\n1 for direct parameter to HexDump\n2 for URL to HexDump\n3 for UserInput to HexDump via Socket and EchoServer");
            System.exit(1);
        }

        //set the stage
        primaryStage.setTitle("HexDump");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //content of the panel
        //set up title
        Text sceneTitle = new Text("What type of input?");
        sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        VBox vbTitle = new VBox();
        vbTitle.setAlignment(Pos.BASELINE_CENTER);
        vbTitle.getChildren().add(sceneTitle);
        grid.add(vbTitle, 0, 0, 1, 1);

        //set up buttons
        Button buttonUrl = new Button("URL");
        Button buttonUrlConnection = new Button("URLConnection");
        Button buttonSocket = new Button("Socket");

        buttonUrl.setMaxWidth(100);
        buttonUrlConnection.setMaxWidth(100);
        buttonSocket.setMaxWidth(100);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(5);
        vbButtons.setAlignment(Pos.BASELINE_CENTER);
        vbButtons.getChildren().addAll(buttonUrl, buttonUrlConnection, buttonSocket);
        grid.add(vbButtons, 0, 1, 1, 1);

        //set up output box
        final Text hexDumpTitle = new Text();
        hexDumpTitle.setFill(Color.CRIMSON);
        VBox vbActionTarget = new VBox();
        vbActionTarget.setAlignment(Pos.BASELINE_CENTER);
        vbActionTarget.getChildren().add(hexDumpTitle);
        grid.add(vbActionTarget, 0, 2, 1, 1);

        //create hexDumpString
        int i, j, k, line = 0;
        String s = new String(hexByte(line, 4)) + ": ";
        String temp;
        String dataString = new String(data);

        for (i = 0; i < dataString.length(); i++) {
            temp = new String(hexByte(data[i], 2));
            s = s + temp;

            if ((i + 1) % 16 == 0 && (i + 1) != dataString.length()) {
                line++;
                temp = new String(hexByte(line, 4));

                s = s + "\t\t" + plainText[i] + "\n" + temp + ": ";
            } else if ((i + 1) % 4 == 0 && (i + 1) != dataString.length()) {
                s = s + " | ";
            } else {
                s = s + " ";
            }
        }

        //set final variable for inner method below
        final String hexDumpString = s;

        //events
        buttonUrl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                URLLoad urlObject = new URLLoad();
                urlObject.setFileText(parameterArray[0], parameterArray[1]);

                hexDumpTitle.setText("HexDump via URL-object");

                System.out.println(hexDumpString);
            }
        });
        buttonUrlConnection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                URLConnectionLoad urlsConnectionObject = new URLConnectionLoad();
                urlsConnectionObject.setFileText(parameterArray[0], parameterArray[1]);

                hexDumpTitle.setText("HexDump via URLConnection-object");

                System.out.println(hexDumpString);
            }
        });
        buttonSocket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Type text to convert to HexDump!");

                SocketLoad socketObject = new SocketLoad();
                socketObject.setFileText(parameterArray[0], parameterArray[1], parameterArray[2]);

                System.out.println("Creating HexDump.");

                hexDumpTitle.setText("HexDump via Socket");

                System.out.println(hexDumpString);
            }
        });

        Scene scene = new Scene(grid, 400, 250);
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
