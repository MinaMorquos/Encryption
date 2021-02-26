package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Third extends AppCompatActivity {


    EditText Txt_plainText3, Txt_key3, Txt_cipherTxt, Txt_type, Txt_RC6,Txt_rc5w2,Txt_rc5w1;
    Button Btn_RC5, Btn_RC6,Btn_next3,Btn_Back3;
    static int w = 32, r = 20;
    static int[] S;
    static int Pw = 0xb7e15163,Qw = 0x9e3779b9;

/*
     public static String RC6Input="";
    public static String typeE="";
     public static String TxtKey="";
  */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_third );
        Txt_plainText3 = findViewById( R.id.Txt_plainText3 );
        Txt_key3 = findViewById( R.id.Txt_key3 );
        Txt_type = findViewById( R.id.Txt_type );
        Txt_rc5w1 = findViewById( R.id.Txt_rc5w1 );
        Btn_Back3 = findViewById( R.id.Btn_Back3 );
        Btn_next3 = findViewById( R.id.Btn_next3 );
        Txt_rc5w2 = findViewById( R.id.Txt_rc5w2 );
        Txt_RC6 = findViewById( R.id.Txt_RC6 );
        Btn_RC6 = findViewById( R.id.Btn_RC6 );
        Txt_cipherTxt = findViewById( R.id.Txt_cipherTxt );
        Btn_RC5 = findViewById( R.id.Btn_RC5 );
        Txt_type.setText( "" );
        Txt_plainText3.setText( "c8 24 18 16 f0 d7 e4 89 20 ad 16 a1 67 4e 5d 48" );
        Txt_key3.setText( "01 23 45 67 89 ab cd ef 01 12 23 34 45 56 67 78 89 9a ab bc cd de ef f0 10 32 54 76 98 ba dc fe" );
     /*   brKey = Txt_plainText3.getText().toString();
        brInput = Txt_key3.getText().toString();
        RC6Input=Txt_plainText3.getText().toString();
        typeE=Txt_type.getText().toString();
        TxtKey=Txt_key3.getText().toString();*/

        //   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Btn_next3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SecondIntent= new Intent( Third.this,MainActivity.class );
                startActivity( SecondIntent );
            }
        } );


        Btn_Back3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent( Third.this,Second.class );
                startActivity( in );
            }
        } );




        Btn_RC5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Txt_rc5w1.length()==0 ||Txt_rc5w2.length()==0)
                {
                    Toast.makeText( Third.this,"Enter Words of Rc5 ",Toast.LENGTH_LONG ).show();

                }else
                {
                    try {
                        if (Txt_type.getText().toString().equals("1"))
                        {
                            encrypt();

                        }else
                        {
                            decrypt();
                        }
                    } catch (Exception e) {
                        Toast.makeText( Third.this,"SomeThing went wrong with Rc5",Toast.LENGTH_SHORT ).show();
                    }
                }


              //  dec.decrypt();
                //  System.out.println("Encryption");
                //  RC5Dec dec = new RC5Dec();
                //  dec.decrypt();

            }
        } );

        Btn_RC6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     Txt_RC6.setText( encryptedRc6 );
                Toast.makeText( Third.this,""+ encryptedRc6,Toast.LENGTH_SHORT).show();
                Log.i("ss",encryptedRc6);*/
                //Txt_RC6.setText(encryptedRc6);

                String tempString;
                String given_text;
                String text_data;
                String key_value;
                String []input_text_val;
                int total_lines;
                BufferedWriter output_to_text_file=null;
                Scanner sc=new Scanner(System.in);
                try {
                    if (Txt_type.getText().toString().equals("1")){
                        given_text="plaintext:"+Txt_plainText3.getText().toString();
                        input_text_val=given_text.split(":");
                        text_data=input_text_val[1];
                        key_value="userkey:"+Txt_key3.getText().toString();
                        String []input_key_val=key_value.split(":");
                        tempString=input_key_val[1];

                        tempString=tempString.replace(" ", "");
                        text_data=text_data.replace(" ", "");
                        byte[] key=hexStringToByteArray(tempString);
                        byte[] W=hexStringToByteArray(text_data);
                        S = KeySchedule(key);
                        byte[] encrypt=encryption(W);
                        String encrypted_text=byteArrayToHex(encrypt);
                        encrypted_text = encrypted_text.replaceAll("..", "$0 ");
                        Txt_RC6.setText(encrypted_text);
                    }
                    else if (Txt_type.getText().toString().equals("0")) {
                        given_text="plaintext:"+Txt_plainText3.getText().toString();
                        input_text_val=given_text.split(":");
                        text_data=input_text_val[1];
                        key_value="userkey:"+Txt_key3.getText().toString();
                        String []input_key_val=key_value.split(":");
                        tempString=input_key_val[1];

                        tempString=tempString.replace(" ", "");
                        text_data=text_data.replace(" ", "");

                        byte[] key2=hexStringToByteArray(tempString);
                        byte[] X=hexStringToByteArray(text_data);
                        S =KeySchedule(key2);
                        byte[] decrypt=decryption(X);
                        String decrypted_text=byteArrayToHex(decrypt);
                        decrypted_text = decrypted_text.replaceAll("..", "$0 ");
                        Txt_RC6.setText(decrypted_text);


                    }
                }
                catch (Exception e)
                {
                    Toast.makeText( Third.this,"SomeThing went wrong with Rc6",Toast.LENGTH_SHORT ).show();

                }



            }
        } );




    }
///RC6
    public static byte[] hexStringToByteArray(String s) {
        int string_len = s.length();
        byte[] data = new byte[string_len / 2];
        for (int i = 0; i < string_len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }



    // CODE TO CONVERT BYTE ARRAY TO HEX FORMAT
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }



    // KEY SCHEDULING ALGORITHM
    public int[] KeySchedule(byte[] key) {


        int[] S = new int[2 * r + 4];
        S[0] = Pw;

        int c = key.length / (w/8);
        int[] L = bytestoWords(key,  c);

        for (int i = 1; i < (2 * r + 4); i++){
            S[i] = S[i - 1] + Qw;
        }

        int A,B,i,j;

        A=B=i=j=0;

        int v = 3 * Math.max(c, (2 * r + 4));

        for (int s = 0; s < v; s++) {
            A = S[i] = rotateLeft((S[i] + A + B), 3);
            B = L[j] = rotateLeft(L[j] + A + B, A + B);
            i = (i + 1) % (2 * r + 4);
            j = (j + 1) % c;

        }

        return S;
    }




    // ENCRYPTION ALGORITHM
    public byte[] encryption(byte[] keySchArray){


        int temp,t,u;

        int[] temp_data = new int[keySchArray.length/4];

        for(int i =0;i<temp_data.length;i++)
            temp_data[i] = 0;


        temp_data=convertBytetoInt(keySchArray,temp_data.length);


        int A,B,C,D;

        A=B=C=D=0;


        A = temp_data[0];
        B = temp_data[1];
        C = temp_data[2];
        D = temp_data[3];



        B = B + S[0];
        D = D + S[1];

        int lgw=5;

        byte[] outputArr = new byte[keySchArray.length];
        for(int i = 1;i<=r;i++){

            t = rotateLeft(B*(2*B+1),lgw);
            u = rotateLeft(D*(2*D+1),lgw);
            A = rotateLeft(A^t,u)+S[2*i];
            C = rotateLeft(C^u,t)+S[2*i+1];

            temp = A;
            A = B;
            B = C;
            C = D;
            D = temp;
        }

        A = A + S[2*r+2];
        C = C + S[2*r+3];

        temp_data[0] = A;
        temp_data[1] = B;
        temp_data[2] = C;
        temp_data[3] = D;


        outputArr = convertIntToByte(temp_data,keySchArray.length);


        return outputArr;
    }

    //DECRYPTION ALGORITHM
    public byte[] decryption(byte[] keySchArray){


        int temp,t,u;
        int A,B,C,D;

        A=B=C=D=0;
        int[] temp_data_decryption = new int[keySchArray.length/4];

        for(int i =0;i<temp_data_decryption.length;i++)
            temp_data_decryption[i] = 0;


        temp_data_decryption=convertBytetoInt(keySchArray,temp_data_decryption.length);


        A = temp_data_decryption[0];
        B = temp_data_decryption[1];
        C = temp_data_decryption[2];
        D = temp_data_decryption[3];




        C = C - S[2*r+3];
        A = A - S[2*r+2];

        int lgw=5;

        byte[] outputArr = new byte[keySchArray.length];
        for(int i = r;i>=1;i--){
            temp = D;
            D = C;
            C = B;
            B = A;
            A = temp;

            u = rotateLeft(D*(2*D+1),lgw);
            t = rotateLeft(B*(2*B+1),lgw);
            C= rotateRight(C-S[2*i+1],t)^u;
            A= rotateRight(A-S[2*i], u)^t;

        }
        D=D-S[1];
        B=B-S[0];


        temp_data_decryption[0] = A;
        temp_data_decryption[1] = B;
        temp_data_decryption[2] = C;
        temp_data_decryption[3] = D;


        outputArr = convertIntToByte(temp_data_decryption,keySchArray.length);


        return outputArr;
    }


    // CONVERT INT TO BYTE FORM
    public static byte[] convertIntToByte(int[] integerArray,int length){
        byte[]  int_to_byte=new byte[length];
        for(int i = 0;i<length;i++){
            int_to_byte[i] = (byte)((integerArray[i/4] >>> (i%4)*8) & 0xff);
        }

        return int_to_byte;
    }


    // CONVERT BYTE TO INT FORM
    private static int[] convertBytetoInt(byte[] arr,int length){
        int[]  byte_to_int=new int[length];
        for(int j=0; j<byte_to_int.length; j++)
        {
            byte_to_int[j] = 0;
        }

        int counter = 0;
        for(int i=0;i<byte_to_int.length;i++){
            byte_to_int[i] = ((arr[counter++]&0xff))|
                    ((arr[counter++]&0xff) << 8) |
                    ((arr[counter++]&0xff) << 16) |
                    ((arr[counter++]&0xff) << 24);
        }
        return byte_to_int;

    }



    // CONVERT BYTE TO WORDS
    private static int[] bytestoWords(byte[] userkey,int c) {
        int[] bytes_to_words = new int[c];
        for (int i = 0; i < bytes_to_words.length; i++)
            bytes_to_words[i] = 0;

        for (int i = 0, off = 0; i < c; i++)
            bytes_to_words[i] = ((userkey[off++] & 0xFF)) | ((userkey[off++] & 0xFF) << 8)
                    | ((userkey[off++] & 0xFF) << 16) | ((userkey[off++] & 0xFF) << 24);

        return bytes_to_words;
    }


    // ROTATE LEFT METHOD
    private static int rotateLeft(int val, int pas) {
        return (val << pas) | (val >>> (32 - pas));
    }

    //ROTATE RIGHT METHOD
    private static int rotateRight(int val, int pas) {
        return (val >>> pas) | (val << (32-pas));
    }






    /////RC5 Encrypt

    public void encrypt() throws Exception{
        String s[] = compute();
        System.out.print("a = ");
        String a = fullfill0(Txt_rc5w1.getText().toString());
        System.out.print("b = ");
        String b = fullfill0(Txt_rc5w2.getText().toString());
        a=a.replace(" ", "");
        b=b.replace(" ", "");
        a = add(a, fullfill0(s[0]));
        b = add(b, fullfill0(s[1]));
        int tmp = 0;
        for (int i = 1; i <= 12; i++) {
            tmp = Integer.parseInt(""+Long.parseLong(b,2)%32);
            a = xor(a, b);
            a = a.substring(tmp)+a.substring(0,tmp);
            a = add(a, fullfill0(s[2 * i]));
            tmp = Integer.parseInt(""+Long.parseLong(a,2)%32);
            b = xor(b, a);
            b = b.substring(tmp)+b.substring(0,tmp);
            b = add(b, fullfill0(s[(2 * i)+1]));
            // System.out.println(i+" iteration = "+(Long.toHexString(Long.parseLong(a,2)))+(Long.toHexString(Long.parseLong(b,2))));
        }
        String out = a+b;
      //  System.out.println("Output = "+(Long.toHexString(Long.parseLong((out.substring(0,32)),2)))+(Long.toHexString(Long.parseLong((out.substring(32)),2))));
        Txt_cipherTxt.setText((Long.toHexString(Long.parseLong((out.substring(0,32)),2)))+(Long.toHexString(Long.parseLong((out.substring(32)),2))) );
    }

    ////// Rc5 Dec
    public void decrypt() throws Exception{
        String s[] = compute();
        System.out.print("a = ");
        String a = fullfill0(Txt_rc5w1.getText().toString());
        a=a.replace(" ", "");
        System.out.print("b = ");
        String b = fullfill0(Txt_rc5w2.getText().toString());
        b=b.replace(" ", "");

        int tmp = 0;
        for (int i = 12; i >= 1; i--) {
            b = fullfill0(Long.toBinaryString((Long.parseLong(b, 2) - Long.parseLong(s[(2*i)+1], 2))));
            b = b.substring(b.length()-32);
            tmp = Integer.parseInt(""+Long.parseLong(a,2)%32);
            b = b.substring(b.length()-tmp) + b.substring(0,b.length()-tmp);
            b = xor(b, a);
            a = fullfill0(Long.toBinaryString((Long.parseLong(a, 2) - Long.parseLong(s[2*i], 2) )));
            a = a.substring(a.length()-32);
            tmp = Integer.parseInt(""+Long.parseLong(b,2)%32);
            a = a.substring(a.length()-tmp) + a.substring(0,a.length()-tmp);
            a = xor(a, b);
            System.out.println(i+ " iteration = "+(Long.toHexString(Long.parseLong(a,2)))+(Long.toHexString(Long.parseLong(b,2))));
        }
        a = fullfill0(Long.toBinaryString((Long.parseLong(a, 2) - Long.parseLong(s[0], 2))));
        b = fullfill0(Long.toBinaryString((Long.parseLong(b, 2) - Long.parseLong(s[1], 2))));
        String output = a+b;
        output = output.substring(output.length()-64);
        System.out.println("Output = "+(Long.toHexString(Long.parseLong(output.substring(0,32),2)))+(Long.toHexString(Long.parseLong(output.substring(32),2))));
    }

    public String fullfill0(String x) {
        return (get0(32-x.length())+ x);
    }

    public String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }

        }
        return result;
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

    public String add(String x, String y) {
        String result = "";
        boolean carry = false;
        for (int i = x.length()- 1; i >= 0; i--) {
            if((x.charAt(i) == y.charAt(i) && carry == false)|| (x.charAt(i) != y.charAt(i)  && carry == true)){
                result = "0"+result;
            }else{
                result = "1"+result;
            }
            if((x.charAt(i) == '1' && y.charAt(i) == '1') ||
                    (x.charAt(i) == '1' && y.charAt(i) == '1' && carry == true) ||
                    (x.charAt(i) !=  y.charAt(i) && carry == true)){carry = true;}
            else{carry = false;}
        }
        return result;
    }

    //RC5

    public String[] compute() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter uKey = ");
        String ukey = fullfill0(Txt_key3.getText().toString());
        ukey=ukey.replace(" ", "");
        String s[] = new String[26];
        String l[] = new String[4];
        s[0] = Long.toBinaryString(Long.parseLong("B7E15163", 16));//(Pw)
        for (int i = 1; i < s.length; i++) {
            s[i] = add(s[i - 1], fullfill0(Long.toBinaryString(Long.parseLong("9E3779B9", 16))));//S[i] = S[i-1]+ 0x9E3779B9 (Qw)
        }
        for (int i = 0; i< l.length ; i++) {
            l[i] = (Long.toBinaryString(Long.parseLong(ukey.substring((3-i) * 8, (((3-i) + 1) * 8)), 16)));//S[i] = S[i-1]+ 0x9E3779B9 (Qw)
        }
        for (int i = 0; i< l.length ; i++) {
            System.out.println("l = "+(Long.toHexString(Long.parseLong(l[i],2))));
        }

        int i = 0, j = 0;
        String a = "", b = "",temp = "";
        for (int count = 0; count < 78; count++) {
            //A = S[i] = (S[i] + A + B) <<< 3;
            temp = add(fullfill0(s[i]) ,add(fullfill0(a), fullfill0(b)));
            a = s[i] = temp.substring(3)+temp.substring(0,3);
            //B = L[j] = (L[j] + A + B) <<< (A + B);
            temp = add(fullfill0(l[j]) ,add(fullfill0(a), fullfill0(b)));
            long le  = (Long.parseLong(add(fullfill0(a), fullfill0(b)),2))%32;
            int len  = Integer.parseInt(""+le);
            b = l[j] = temp.substring(len)+temp.substring(0,len);
            i = (i + 1) % 26;
            j = (j + 1) % 4;
        }
        return s;
    }





}


