import java.lang.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Edwin on 26/05/2016.
 */
public class HexDump
{

    private byte data[];

    public static void main(String args[]){

        try
        {
            System.out.println(new HexDump(args[0]).getHexString());
            //System.out.println(new HexDump(new FileInputStream(args[0])).getHexString());
        }catch(Exception e){System.out.println(e);}
    }

    private HexDump(String s){
        data=s.getBytes();
    }

    private HexDump(InputStream fis){
        try
        {
            ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
            byte buf[]=new byte[1024];
            int lenr;

            while ((lenr=fis.read(buf))>-1){
                bos.write(buf,0,lenr);
            }

            data=bos.toByteArray();
        }catch(Exception e){System.out.println(e);}
    }

    /* produces a char Array of length len displaying
       the hexadecimal Value of the value z */
    private char[] hexByte(int z, int len){
        char[] x=new char[len];
        int i,hx;
        for(i=len-1;i>=0;i--)
        {
            hx=z;
            z>>>=4;
            hx&=0xf;
            x[i]=(char)(hx<=9?hx+'0':hx+'A'-10);
        }
        return x;
    }

    private String HexByteString(int pos){
        return Arrays.toString(hexByte(data[pos], 2));
    }

    /* produces a String in the Form of a HexDump
       of an array of bytes */
    private String getHexString(){
        String s = "";
        int line = 0;

        String dataString = Arrays.toString(data);

        s = s + "" + Arrays.toString(hexByte(line, 4));

        for (int i = 0; i < dataString.length(); i++) {
            s = s + HexByteString(i);

            if (i % 4 == 0) {
                s = s + " | ";
            } else {
                s = s + " ";
            }

            if (i % 16 == 0){
                line ++;
                s = s + "\n" + Arrays.toString(hexByte(line, 4));
            }
        }





        // geben Sie zuerst den Text in data als String zurueck, erzeugen Sie dazu ein
        // neues Objekt der Klasse String und initialisieren es mit data.
        // Als zweites erzeugen Sie ein Stringobjekt aus dem Returnwert der
        // Funktion getHexByte, der Sie das erste Byte von Data und die
        // Laenge 2 uebergeben.
        // Als drittes bauen Sie eine Schleife, in der die Bytes aus Data
        // mit getHexByte umgewandelt werden und nacheinander, durch Leerzeichen
        // getrennt zu dem String zusammen gebaut werden.

        return s;
    }

}