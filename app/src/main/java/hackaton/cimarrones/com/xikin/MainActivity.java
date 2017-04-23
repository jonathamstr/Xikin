package hackaton.cimarrones.com.xikin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    ArrayList<ImageView> oracion;
    ArrayList<ImageButton> posiblesBotones;
    ArrayList<String> sugerencias;
    ArrayList<Integer> posiblesImagenes;
    String escrito;
    String frase;
    int added;
    private ImageView speechButton;
    private TextToSpeech engine;
    private EditText editText;
    private SeekBar seekPitch;
    private SeekBar seekSpeed;
    private double pitch=1.4;
    private double speed=.9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posiblesBotones = new ArrayList<ImageButton>();
        posiblesImagenes =new ArrayList<Integer>();
        sugerencias = new ArrayList<String>(ServicePalabras.getPalabras());
        engine = new TextToSpeech(this, this);
        setContentView(R.layout.teclado);
        LinearLayout oracionLista = (LinearLayout) findViewById(R.id.oracionView);
        LinearLayout list = (LinearLayout)findViewById(R.id.oracionView);
        oracion = new ArrayList<ImageView>();

        for(int i=0;i<10;i++){

            oracion.add((ImageView) list.getChildAt(i));
        }
        escrito = "";
        frase = "";
        added = 0;

        posiblesBotones.add((ImageButton) findViewById(R.id.p0));
        posiblesBotones.add((ImageButton) findViewById(R.id.p1));
        posiblesBotones.add((ImageButton) findViewById(R.id.p2));
        posiblesBotones.add((ImageButton) findViewById(R.id.p3));
        posiblesBotones.add((ImageButton) findViewById(R.id.p4));
        posiblesBotones.add((ImageButton) findViewById(R.id.p5));
        posiblesBotones.add((ImageButton) findViewById(R.id.p6));
        posiblesBotones.add((ImageButton) findViewById(R.id.p7));
        posiblesBotones.add((ImageButton) findViewById(R.id.p8));
        posiblesBotones.add((ImageButton) findViewById(R.id.p9));
    }

    public void agregar(View view){


        String letter = String.valueOf(view.getTag());speech(letter);
        posiblesImagenes.clear();
        escrito += letter;

        Toast.makeText(getApplicationContext(),escrito,Toast.LENGTH_SHORT).show();
        GridLayout posiblesGrid = (GridLayout)findViewById(R.id.posiblesPalabras);

        List<String> posibles = new ArrayList<String>();
        for(String temp: sugerencias){
            if(temp.contains(escrito)){
                posibles.add(temp);
                int imagen = getResources().getIdentifier("drawable/"+temp,null,getPackageName());
                posiblesImagenes.add(imagen);
            }
        }

        for(int i = 0; i<10;i++){
            if(i<posibles.size()){
                posiblesBotones.get(i).setTag(posibles.get(i));
                posiblesBotones.get(i).setImageResource(posiblesImagenes.get(i));
                posiblesBotones.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        agregarPalabra(v);
                    }
                });
            }
            else{
                posiblesBotones.get(i).setImageResource(R.drawable.gri);
                //posiblesBotones.get(i).setImageResource();
            }

        }
        /*List<Integer> intPosibles = new ArrayList<Integer>();
        for(String temp:posibles){
            int imagen = getResources().getIdentifier("drawable/"+temp,null,getPackageName());

        }*/
        //CustomAdapter adapter = new CustomAdapter(MainActivity.this, intPosibles);
        /*for(int i=0;i<10&&i<posibles.size();i++)
        {
            CustomAdapter adapter = new CustomAdapter(MainActivity.t);
            ImageButton button = new ImageButton(getApplicationContext());
            button.setImageResource(R.drawable.a);
            Toast.makeText(getApplicationContext(),posibles.get(i),Toast.LENGTH_SHORT).show();
            posiblesGrid.addView(button,i,0);
            button.setTag(posibles.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarPalabra(v);
                }
            });
        }
        */
        for(String temp:posibles){
            ImageButton button = new ImageButton(getApplicationContext());
            button.setImageResource(getResources().getIdentifier("drawable/"+temp,null,getPackageName()));

        }


    }

    public void agregarPalabra(View view){
        if(added<10){
            escrito = "";
            ImageButton button = (ImageButton)view;
            ImageView vistaImage = new ImageView(getApplicationContext());
            vistaImage.setImageResource(getResources().getIdentifier("drawable/"+button.getTag(),null,getPackageName()));
            frase += button.getTag() + " ";
            oracion.get(added).setImageResource(getResources().getIdentifier("drawable/"+button.getTag(),null,getPackageName()));
            added++;
        }

    }

    public void borrar(View view){
        GridLayout grid  = (GridLayout)findViewById(R.id.gridLayout);
       // grid.removeAllViews();

        escrito = "";
    }
    int contador=0;

    public void hablar(View view){
        //Hablar frase

        speech(frase);
        frase = "";
        borrar(view);
        for(ImageButton temp:posiblesBotones){
            temp.setImageResource(R.drawable.gri);
        }

        for(ImageView temp:oracion){
            temp.setImageResource(R.drawable.gri);
        }
        if(contador==3){

            Intent i =new Intent(MainActivity.this,Escuchar.class);
            startActivity(i);
            contador=0;
        }else{contador++;}


        added = 0;


    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            Locale spanish = new Locale("es", "ES");
            engine.setLanguage(spanish);
        }
    }

    private void speech(String hablar) {
        engine.setPitch((float) pitch);
        engine.setSpeechRate((float) speed);
        //engine.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
        engine.speak(hablar, TextToSpeech.QUEUE_FLUSH, null);
    }


}
