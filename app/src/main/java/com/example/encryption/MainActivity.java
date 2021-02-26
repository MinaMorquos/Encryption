package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;




public class MainActivity extends AppCompatActivity {

    String xx;
    EditText Txt_Key,Txt_Cipher,Txt_PlainText,Txt_playFairMatrix,Txt_PlayFairEncrypt,Txt_Vigenere;
    Button Btn_Encrypt,Btn_PlayFair,Btn_Clear,Btn_Next,Btn_Vigenere ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Txt_Key = findViewById( R.id.Txt_Key );
        Txt_Cipher = findViewById( R.id.Txt_Cipher );
        Txt_PlainText = findViewById( R.id.Txt_PlainText );
        Txt_PlayFairEncrypt = findViewById( R.id.Txt_PlayFairEncrypt );
        Txt_playFairMatrix = findViewById( R.id.Txt_playFairMatrix );
        Txt_Vigenere = findViewById( R.id.Txt_Vigenere );
        Btn_Encrypt=findViewById( R.id.Btn_Encrypt );
        Btn_PlayFair=findViewById( R.id.Btn_PlayFair );
        Btn_Clear=findViewById( R.id.Btn_Clear );
        Btn_Next=findViewById( R.id.Btn_Next );
        Btn_Vigenere=findViewById( R.id.Btn_Vigenere);
        Txt_Cipher.setKeyListener(null);
        Txt_PlayFairEncrypt.setKeyListener(null);
        Txt_playFairMatrix.setKeyListener(null);
        Txt_Vigenere.setKeyListener(null);



        Btn_Vigenere.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Txt_PlainText.length()==0)
                {
                    Toast.makeText( MainActivity.this,"Enter Plain Text",Toast.LENGTH_LONG ).show();

                }else{
                    String text = Txt_PlainText.getText().toString();;
                    String key = Txt_Key.getText().toString();
                    String ciphertext = encrypt(text, key);
                    String x = ciphertext;
                    Txt_Vigenere.setText( x );
                }



            }
        } );






        Btn_Next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SecondIntent= new Intent( MainActivity.this,Second.class );
                    startActivity( SecondIntent );
            }
        } );


        Btn_Clear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Key.setText( "" );
                Txt_Cipher.setText( "" );
                Txt_PlainText.setText( "" );
                Txt_PlayFairEncrypt.setText( "" );
                Txt_playFairMatrix.setText( "" );
                Txt_Vigenere.setText( "" );
            }
        } );




        Btn_Encrypt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Txt_PlainText.length()==0)
                {
                    Toast.makeText( MainActivity.this,"Enter Plain Text",Toast.LENGTH_LONG ).show();

                }else{
                    String plaintext = Txt_PlainText.getText().toString();
                    String key = Txt_Key.getText().toString();
                    String final_key = "", ciphertext = "";

                    ArrayList<ArrayList<Character>> vignere = new ArrayList<>();
                    String alphabet = "abcdefghijklmnopqrstuvwxyz";
                    for (int i = 0; i < 26; i++) {
                        ArrayList<Character> row_vignere = new ArrayList<>();
                        for (int j = 0; j < 26; j++)
                            row_vignere.add(alphabet.charAt((i + j) % 26));
                        vignere.add(row_vignere);
                    }

                    for (int i = 0, j = 0; i < plaintext.length(); i++) {
                        if (plaintext.charAt(i) >= 'a' && plaintext.charAt(i) <= 'z')
                            final_key += key.charAt((j++) % key.length());
                        else
                            final_key += plaintext.charAt(i);
                    }
                    for (int i = 0; i < plaintext.length(); i++) {
                        if (plaintext.charAt(i) >= 'a' && plaintext.charAt(i) <= 'z')
                            ciphertext += vignere.get(alphabet.indexOf("" + final_key.charAt(i))).get(alphabet.indexOf("" + plaintext.charAt(i)));
                        else
                            ciphertext += plaintext.charAt(i);
                    }

                    Txt_PlainText.setText( plaintext );
                    Txt_Key.setText( key );
                    Txt_Cipher.setText( ciphertext );

                }

            }
        } );

        Btn_PlayFair.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   if (Txt_PlainText.getText().toString().equals(""))
                if (Txt_PlainText.length()==0)
                {
                    Toast.makeText( MainActivity.this,"Enter Plain Text",Toast.LENGTH_LONG ).show();

                }else {
                    PlayfairCipherEncryption x = new PlayfairCipherEncryption();

                    x.setKey( Txt_Key.getText().toString() );
                    x.KeyGen();


                    if ( Txt_PlainText.getText().length() % 2 == 0)
                    {
                        Txt_playFairMatrix.setText( x.Key);
                        Txt_PlayFairEncrypt.setText( x.encryptMessage( Txt_PlainText.getText().toString() ) );
                    }
                    else
                    {
                        Toast.makeText( MainActivity.this,"Message length should be even",Toast.LENGTH_LONG ).show();

                    }


                }



            }
        } );

    }

    public static String encrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    public static String decrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }



    public class PlayfairCipherEncryption    {
        private String KeyWord        = new String();
        private String Key            = new String();
        private char   matrix_arr[][] = new char[5][5];

        public void setKey(String k)
        {
            String K_adjust = new String();
            boolean flag = false;
            K_adjust = K_adjust + k.charAt(0);
            for (int i = 1; i < k.length(); i++)
            {
                for (int j = 0; j < K_adjust.length(); j++)
                {
                    if (k.charAt(i) == K_adjust.charAt(j))
                    {
                        flag = true;
                    }
                }
                if (flag == false)
                    K_adjust = K_adjust + k.charAt(i);
                flag = false;
            }
            KeyWord = K_adjust;
        }

        public void KeyGen()
        {
            boolean flag = true;
            char current;
            Key = KeyWord;
            for (int i = 0; i < 26; i++)
            {
                current = (char) (i + 97);
                if (current == 'j')
                    continue;
                for (int j = 0; j < KeyWord.length(); j++)
                {
                    if (current == KeyWord.charAt(j))
                    {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    Key = Key + current;
                flag = true;
            }
            System.out.println(Key);
            matrix();
        }

        private void matrix()
        {
            int counter = 0;
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    matrix_arr[i][j] = Key.charAt(counter);
                    System.out.print(matrix_arr[i][j] + " ");
                    counter++;
                }
                System.out.println();
            }
        }

        private String format(String old_text)
        {
            int i = 0;
            int len = 0;
            String text = new String();
            len = old_text.length();
            for (int tmp = 0; tmp < len; tmp++)
            {
                if (old_text.charAt(tmp) == 'j')
                {
                    text = text + 'i';
                }
                else
                    text = text + old_text.charAt(tmp);
            }
            len = text.length();
            for (i = 0; i < len; i = i + 2)
            {
                if (text.charAt(i + 1) == text.charAt(i))
                {
                    text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
                }
            }
            return text;
        }

        private String[] Divid2Pairs(String new_string)
        {
            String Original = format(new_string);
            int size = Original.length();
            if (size % 2 != 0)
            {
                size++;
                Original = Original + 'x';
            }
            String x[] = new String[size / 2];
            int counter = 0;
            for (int i = 0; i < size / 2; i++)
            {
                x[i] = Original.substring(counter, counter + 2);
                counter = counter + 2;
            }
            return x;
        }

        public int[] GetDiminsions(char letter)
        {
            int[] key = new int[2];
            if (letter == 'j')
                letter = 'i';
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    if (matrix_arr[i][j] == letter)
                    {
                        key[0] = i;
                        key[1] = j;
                        break;
                    }
                }
            }
            return key;
        }

        public String encryptMessage(String Source)
        {
            String src_arr[] = Divid2Pairs(Source);
            String Code = new String();
            char one;
            char two;
            int part1[] = new int[2];
            int part2[] = new int[2];
            for (int i = 0; i < src_arr.length; i++)
            {
                one = src_arr[i].charAt(0);
                two = src_arr[i].charAt(1);
                part1 = GetDiminsions(one);
                part2 = GetDiminsions(two);
                if (part1[0] == part2[0])
                {
                    if (part1[1] < 4)
                        part1[1]++;
                    else
                        part1[1] = 0;
                    if (part2[1] < 4)
                        part2[1]++;
                    else
                        part2[1] = 0;
                }
                else if (part1[1] == part2[1])
                {
                    if (part1[0] < 4)
                        part1[0]++;
                    else
                        part1[0] = 0;
                    if (part2[0] < 4)
                        part2[0]++;
                    else
                        part2[0] = 0;
                }
                else
                {
                    int temp = part1[1];
                    part1[1] = part2[1];
                    part2[1] = temp;
                }
                Code = Code + matrix_arr[part1[0]][part1[1]]
                        + matrix_arr[part2[0]][part2[1]];
            }
            return Code;
        }




    }

}