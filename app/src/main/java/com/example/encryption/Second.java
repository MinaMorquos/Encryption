package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Second extends AppCompatActivity {

    EditText Txt_Text,Txt_caesarShift,Txt_caesarCipher,Txt_Mono,Txt_monoKey;
    Button Btn_Caesar,Btn_Mono,Btn_Clear2,Btn_back,Btn_next2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);
                Txt_Text = findViewById( R.id. Txt_Text);
                Txt_caesarShift = findViewById( R.id. Txt_caesarShift);
                Txt_caesarCipher = findViewById( R.id. Txt_caesarCipher);
                Txt_Mono = findViewById( R.id. Txt_Mono);
                Txt_monoKey = findViewById( R.id. Txt_monoKey);
                Btn_Caesar = findViewById( R.id. Btn_Caesar);
                Btn_Mono = findViewById( R.id. Btn_Mono);
                Btn_Clear2 = findViewById( R.id. Btn_Clear2);
                Btn_next2=findViewById( R.id.Btn_next2 );
                Btn_back = findViewById( R.id. Btn_back);
                Txt_monoKey.setKeyListener(null);
                Txt_caesarCipher.setKeyListener(null);
                Txt_Mono.setKeyListener(null);

        Btn_next2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SecondIntent= new Intent( Second.this,Third.class );
                startActivity( SecondIntent );
            }
        } );


        Btn_back.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent( Second.this,MainActivity.class );
                        startActivity( in );
                    }
                } );

                Btn_Clear2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Txt_Text.setText( "" );
                        Txt_caesarShift.setText( "" );
                        Txt_caesarCipher.setText( "" );
                        Txt_Mono.setText( "" );
                        Txt_monoKey.setText( "" );

                    }
                } );




        Btn_Caesar.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Txt_Text.length()==0)
                        {
                            Toast.makeText( Second.this,"Enter Plain Text",Toast.LENGTH_LONG ).show();

                        }else {
                            String text = Txt_Text.getText().toString();
                            int s = Integer.parseInt(Txt_caesarShift.getText().toString());
                            Txt_caesarCipher.setText( encrypt( text,s ) );

                        }



                          }
                    } );

                    Btn_Mono.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Txt_Text.length()==0)
                            {
                                Toast.makeText( Second.this,"Enter Plain Text",Toast.LENGTH_LONG ).show();

                            }else {
                                MonoAlphabetic mono = new MonoAlphabetic();
                                String key=mono.getCharsPermutation();
                                Txt_monoKey.setText( key );
                                Txt_Mono.setText( mono.encrypt( Txt_Text.getText().toString(),key ) );
                            }




           }
        } );


    }

//
    public static StringBuffer encrypt(String text, int s)
    {
        StringBuffer result= new StringBuffer();

        for (int i=0; i<text.length(); i++)
        {
            if (Character.isUpperCase(text.charAt(i)))
            {
                char ch = (char)(((int)text.charAt(i) +
                        s - 65) % 26 + 65);
                result.append(ch);
            }
            else
            {
                char ch = (char)(((int)text.charAt(i) +
                        s - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }


    // Mono
    public class MonoAlphabetic {

        private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

        public String getCharsPermutation() {//Get shuffled alphabet

            Set set = new LinkedHashSet(); // I use linkedhashset because we don't want a sorted set
            SecureRandom random;
            StringBuilder chars = new StringBuilder();
            try {
                random = SecureRandom.getInstance("SHA1PRNG");
                while (set.size() < 26) {
                    set.add(alphabet.charAt(random.nextInt(alphabet.length())));
                }
                for (Object object : set) {
                    chars.append(object);
                }
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("No such Algorithm");
            }
            return chars.toString();
        }

        public String encrypt(String plaintext, String key) {
            StringBuilder ciphertext = new StringBuilder();

            for (char chr : plaintext.toLowerCase().toCharArray()) {
                byte position = (byte) alphabet.indexOf(chr);
                if (chr == ' ') {
                    ciphertext.append(" ");
                } else {
                    ciphertext.append(key.charAt(position));
                }
            }

            return ciphertext.toString();
        }


    }


}