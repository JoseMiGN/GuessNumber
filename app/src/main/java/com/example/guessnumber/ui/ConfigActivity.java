package com.example.guessnumber.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guessnumber.R;
import com.example.guessnumber.data.model.Jugador;
import com.example.guessnumber.data.model.Numero;
import com.example.guessnumber.databinding.ActivityConfigBinding;

import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Clase que pide al usuario un jugador y un número limite de intentos para adivinar
 * Comprueba que los campos no estén vacíos o tengan algún tipo de error.
 * Un Button que al hacer click cambia a la siguiente Activity
 *  * @author José Miguel Godoy
 *  * @version 1.0
 */
public class ConfigActivity extends AppCompatActivity {

    ActivityConfigBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btPlay.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            Jugador jugador = new Jugador();
            Numero numero = new Numero();

            if (comprobarCampos(jugador, numero)){
                bundle.putSerializable("numero", numero);
                Intent intent = new Intent(ConfigActivity.this, PlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public Boolean comprobarCampos(Jugador jugador, Numero numero) {
        if (binding.edName.getText().toString().isEmpty() || binding.edNumeroMaximoIntentos.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.ErrorCamposConfigActivity), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                Random rnd = new Random();
                jugador.setNombre(binding.edName.getText().toString());
                numero.setIntentosMAX(Integer.parseInt(binding.edNumeroMaximoIntentos.getText().toString()));
                numero.setJugador(jugador);
                numero.setNumeroAdivinar(rnd.nextInt(100));
                return true;
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.ErrorFormatExceptionCofigActivity), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}