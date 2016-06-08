import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Tom on 08.06.2016.
 */
public class SocketLoad {
    public void setFileText(String hostParameter, String fileParameter, String portParameter){

        String hostName = hostParameter;
        int portNumber = Integer.parseInt(portParameter);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            userInput = stdIn.readLine();
            out.println(userInput);
            PrintWriter printWriter = new PrintWriter(fileParameter);
            printWriter.println(in.readLine());
            printWriter.close();

            System.out.println("TextFile has been set.");

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
