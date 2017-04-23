package hackaton.cimarrones.com.xikin;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Escuchar extends AppCompatActivity {


    ArrayList<String> reconocidos;
    TextView grabar;
    List<String> palabras;
    ArrayList<ImageView> imagenes;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagenes = new ArrayList<ImageView>();
        LinearLayout lista = (LinearLayout)findViewById(R.id.oracionView);
        palabras = ServicePalabras.getPalabras();
        setContentView(R.layout.escuchar);
        reconocidos = new ArrayList<String>();
        grabar = (TextView) findViewById(R.id.txtGrabarVoz);
        /*for(int i = 0;i<10;i++){
            imagenes.add((ImageView) lista.getChildAt(i));
        }*/
        imagenes.add((ImageView)findViewById(R.id.sign1));
        imagenes.add((ImageView)findViewById(R.id.sign2));
        imagenes.add((ImageView)findViewById(R.id.sign3));
        imagenes.add((ImageView)findViewById(R.id.sign4));
        imagenes.add((ImageView)findViewById(R.id.sign5));
        imagenes.add((ImageView)findViewById(R.id.sign6));
        imagenes.add((ImageView)findViewById(R.id.sign7));
        imagenes.add((ImageView)findViewById(R.id.sign8));
        imagenes.add((ImageView)findViewById(R.id.sign9));
        imagenes.add((ImageView)findViewById(R.id.sign10));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reconocidos.clear();
        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    String[] encontradas = (String[]) palabras.toArray();
                        grabar.setText(strSpeech2Text);
                                for(String temp:strSpeech2Text.split(" ")){
                            for (String temporales:palabras){
                                if(temp.equals(temporales)){
                                    reconocidos.add(temp);
                                }
                            }
                        }
                        for(int i =0;i<10&&i<reconocidos.size();i++){
                            imagenes.get(i).setImageResource(getResources().getIdentifier("drawable/"+reconocidos.get(i),null,getPackageName()));
                        }
                }

                break;
            default:

                break;
        }
    }

    public void onClickImgBtnHablar(View v) {

        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Lenguaje (Español-México)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }

    }

}