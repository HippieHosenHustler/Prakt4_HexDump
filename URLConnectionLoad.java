import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tom on 08.06.2016.
 */
public class URLConnectionLoad {
    public void setFileText(String urlParameter, String fileParameter){
        try{
            URL url = new URL(urlParameter);
            URLConnection connection = url.openConnection();
            connection.connect();
            OutputStream outputStream = new FileOutputStream(fileParameter);
            InputStream inputStream = connection.getInputStream();

            int length;
            byte buffer[] = new byte[128];

            while ((length = inputStream.read(buffer)) >=1)
                outputStream.write(buffer, 0, length);

            outputStream.close();
            inputStream.close();
        }
        catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }
}
