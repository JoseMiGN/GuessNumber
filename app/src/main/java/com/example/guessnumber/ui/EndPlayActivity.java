package com.example.guessnumber.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guessnumber.R;
import com.example.guessnumber.data.model.Numero;
import com.example.guessnumber.databinding.ActivityEndPlayBinding;

import android.content.Intent;
import android.os.Bundle;

/**
 * Clase que recibe el objeto número.
 * si ha acertado, muestra el número de intentos.
 * si ha fallado, muestra el número final.
 * @author Jose Miguel Godoy
 * @version 1.0
 */
public class EndPlayActivity extends AppCompatActivity {

    ActivityEndPlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEndPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Numero numero = (Numero) intent.getExtras().getSerializable("numero");
        if (numero.getHasGanado()) {
            binding.txtResultado.setText(getString(R.string.MensajeAcertarEndPlayActivity) + numero.getIntentosActual().toString());
        } else {
             binding.txtResultado.setText(getString(R.string.MensajeFallarEndPlayActivity) + numero.getNumeroAdivinar().toString());
        }
    }
}