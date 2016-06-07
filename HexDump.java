/**
 * Created by Tom on 06.06.2016.
 */
import java.lang.*;
import java.io.*;
import java.lang.reflect.Array;

public class HexDump
{

    byte data[];

    HexDump(String s)
    {
        data=s.getBytes();
    }

    HexDump(InputStream fis)
    {
        try
        {
            ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
            byte buf[]=new byte[1024];
            int lenr;

            while ((lenr=fis.read(buf))>-1) bos.write(buf,0,lenr);

            data=bos.toByteArray();
        }catch(Exception e){System.out.println(e);}
    }

    /* produces a char Array of length len displaying
       the hexadecimal Value of the value z */
    private char[] hexByte(int z, int len)
    {
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

    /* produces a String in the Form of a hexdump
       of an array of bytes */
    public String getHexString()
    {
        int i, j, line = 0;
        String s = new String(hexByte(line, 4)) + ": ";
        String temp;
        String plainText;

        String dataString = new String(data);

        for (i = 0; i < dataString.length(); i++) {
            temp = new String(hexByte(data[i], 2));
            s = s + temp;

            if((i+1) % 16 == 0 && (i+1) != dataString.length()) {
                line++;
                temp = new String(hexByte(line, 4));
                plainText = "";

                for (j = (i-15); j <= i; j++) {
                    plainText = plainText + new String(hexByte(data[j], 1));
                }

                s = s + "\t\t" + plainText + "\n" + temp + ": ";
            }
            else if((i+1) % 4 == 0 && (i+1) != dataString.length()){
                s = s + " | ";
            }
            else{
                s = s + " ";
            }
        }

        return s;
    }
    public static void main(String args[])
    {

        try
        {
            System.out.println(new HexDump(args[0]).getHexString());
            //System.out.println(new HexDump(new FileInputStream(args[0])).getHexString());
        }catch(Exception e){System.out.println(e);}
    }
}