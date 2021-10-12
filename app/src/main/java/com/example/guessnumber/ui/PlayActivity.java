package com.example.guessnumber.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guessnumber.R;
import com.example.guessnumber.data.model.Jugador;
import com.example.guessnumber.data.model.Numero;
import com.example.guessnumber.databinding.ActivityPlayBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Clase que recibe el objeto numero que se ha inicializado en la clase ConfigActivity
 * Se pide al usuario un número en un EditText haciendo click en el Button comprueba si lo ha adivinado.
 * Si falla, da un mensaje informativo de si el número que tiene que acertar es mayor o menor y muestra otro button
 * para volver a intentarlo.
 * Si acierta o llega al limite de intentos, cambia a la activity EndPlayActivity.
 * @author Jose Miguel Godoy
 * @version 1.0
 */
public class PlayActivity extends AppCompatActivity {

    ActivityPlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btComprobar.setEnabled(true);
        binding.btVolverAIntentar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Numero numero = (Numero) intent.getExtras().getSerializable("numero");
        System.out.println(numero);

        binding.btComprobar.setOnClickListener(view -> {
            if (binding.edNumero.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), getString(R.string.ErrorCampoPlayActivity), Toast.LENGTH_SHORT).show();
                return;
            }
            if (numero.getIntentosActual() < numero.getIntentosMAX()) {
                Integer numeroAComprobar = Integer.parseInt(binding.edNumero.getText().toString());
                comprobarNumero(numeroAComprobar, numero);
            } else {
                numero.setHasGanado(false);
                iniciarEndPlayActivity(numero);
            }
            numero.setIntentosActual(numero.getIntentosActual() + 1);
        });

        binding.btVolverAIntentar.setOnClickListener(view -> {
            binding.edNumero.setText("");
            binding.txtMensaje.setText("");
            binding.txtMensaje.setVisibility(View.INVISIBLE);
            binding.btVolverAIntentar.setVisibility(View.INVISIBLE);
            binding.btComprobar.setVisibility(View.VISIBLE);
            binding.edNumero.setEnabled(true);
        });
    }


    public void comprobarNumero(Integer numeroAComprobar, Numero numero) {
        if (numeroAComprobar.equals(numero.getNumeroAdivinar())) {
            numero.setHasGanado(true);
            iniciarEndPlayActivity(numero);
        } else {
            String text = getString(R.string.MensajeMenorPlayActivity);

            binding.btVolverAIntentar.setVisibility(View.VISIBLE);
            binding.btVolverAIntentar.setEnabled(true);
            binding.btComprobar.setVisibility(View.INVISIBLE);
            binding.edNumero.setEnabled(false);

            if (numeroAComprobar < numero.getNumeroAdivinar())
                text = getString(R.string.MensajeMayorPlayActivity);

            binding.txtMensaje.setText(text);
            binding.txtMensaje.setVisibility(View.VISIBLE);
        }


    }

    public void iniciarEndPlayActivity(Numero numero) {
        binding.btComprobar.setEnabled(false);
        binding.btVolverAIntentar.setEnabled(false);
        binding.edNumero.setEnabled(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("numero", numero);
        Intent intent = new Intent(PlayActivity.this, EndPlayActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}