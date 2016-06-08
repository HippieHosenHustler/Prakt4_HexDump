import java.io.*;
import java.net.URL;

/**
 * Created by Tom on 08.06.2016.
 */
public class URLLoad {
    public void setFileText(String urlParameter, String fileParameter){
        try{
            URL url = new URL(urlParameter);
            OutputStream outputStream = new FileOutputStream(fileParameter);
            InputStream inputStream = url.openStream();

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
