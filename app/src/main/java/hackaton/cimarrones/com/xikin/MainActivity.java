package hackaton.cimarrones.com.xikin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    ArrayList<ImageView> oracion;
    ArrayList<String> sugerencias;
    String escrito;
    String frase;
    int added;
    private ImageView speechButton;
    private TextToSpeech engine;
    private EditText editText;
    private SeekBar seekPitch;
    private SeekBar seekSpeed;
    private double pitch=1.0;
    private double speed=1.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugerencias = new ArrayList<String>(ServicePalabras.getPalabras());
        engine = new TextToSpeech(this, this);
        setContentView(R.layout.teclado);
        GridLayout grid = (GridLayout)findViewById(R.id.gridLayout);
        oracion = new ArrayList<ImageView>();
        for(int i=0;i<10;i++){

            oracion.add((ImageView) grid.getChildAt(i));
        }
        escrito = "";
        frase = "";
        added = 0;
    }

    public void agregar(View view){

        speech("Ya casi terminamos");
        String letter = String.valueOf(view.getTag());

        escrito += letter;
        Toast.makeText(getApplicationContext(),escrito,Toast.LENGTH_SHORT).show();
        GridLayout posiblesGrid = (GridLayout)findViewById(R.id.gridLayout);
        posiblesGrid.removeAllViews();
        List<String> posibles = new ArrayList<String>();
        for(String temp: sugerencias){
            if(temp.contains(escrito)){
                posibles.add(temp);
                Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
            }

        }

        for(int i=0;i<10&&i<posibles.size();i++)
        {
            ImageButton button = new ImageButton(getApplicationContext());
            button.setImageResource(getResources().getIdentifier("drawable/"+posibles.get(i),null,getPackageName()));
            posiblesGrid.addView(button,i,0);
            button.setTag(posibles.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarPalabra(v);
                }
            });
        }

        for(String temp:posibles){
            ImageButton button = new ImageButton(getApplicationContext());
            button.setImageResource(getResources().getIdentifier("drawable/"+temp,null,getPackageName()));

        }


    }

    public void agregarPalabra(View view){
        escrito = "";
        ImageButton button = (ImageButton)view;
        ImageView vistaImage = new ImageView(getApplicationContext());
        vistaImage.setImageResource(getResources().getIdentifier("drawable/"+button.getTag(),null,getPackageName()));
        frase += button.getTag() + " ";
        GridLayout grid = (GridLayout)findViewById(R.id.gridLayout);
        grid.addView(vistaImage,added,0);
    }

    public void borrar(View view){
        GridLayout grid  = (GridLayout)findViewById(R.id.gridLayout);
        grid.removeAllViews();
        frase = "";
        escrito = "";
    }

    public void hablar(View view){
        //Hablar frase


        frase = "";
        borrar(view);

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
