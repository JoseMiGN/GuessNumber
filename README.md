# GuessNumber

### Contenido
- [Layout](#Layout)
- [Clases POJO](#Clases-Pojo)
- [Activities](#Activity)

#### Proyecto finalizado

![imagen1](https://github.com/JoseMiGN/GuessNumber/img/Proyecto_Finalizado_1.jpg)
![imagen2](https://github.com/JoseMiGN/GuessNumber/img/Proyecto_Finalizado_2.jpg)
![imagen3](https://github.com/JoseMiGN/GuessNumber/img/Proyecto_Finalizado_3.jpg)
![imagen4](https://github.com/JoseMiGN/GuessNumber/img/Proyecto_Finalizado_4.jpg)

#### Objetivo del proyecto

El objetivo del proyecto es intentar adivinar un número aleatorio entre 0 y 99 en un número de intentos que pone el usuario.


#### Añadir viewBinding

Añadir al build.gradle el siguiente código:
```Java
viewBinding {
    enabled = true
}
```

#### Layout

##### activity_config.xml

![imagen](https://github.com/JoseMiGN/GuessNumber/img/Layout_activity_config.jpg)

##### activity_end_play.xml

![imagen](https://github.com/JoseMiGN/GuessNumber/img/Layout_activity_end_play.jpg)

##### activity_play.xml

![imagen](https://github.com/JoseMiGN/GuessNumber/img/Layout_activity_play.jpg)


#### Clases POJO
Las dos clases que vamos a crear deberan implementar la interfaz Serializable porque se pasarán en un Intent.

##### Clase Jugador
Es la clase que guarda la información del usuario, guarda el nombre en una variable String.

##### Clase Numero
Es la clase que vamos a enviar en el bundle.
Guarda la información del jugador, además del número maximo de intentos, el número a adivinar,
el contador de intentos y si ha ganado el usuario.


<table>
<tr>
<th> Jugador </th>
<th> Numero </th>
</tr>
<tr>
<td>

```Java
public class Jugador implements Serializable {
    String nombre;

    public Jugador() {
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
```

</td>
<td>

```Java
public class Numero implements Serializable {
    Jugador jugador;
    Integer numeroAdivinar;
    Integer intentosMAX;
    Integer intentosActual;
    Boolean hasGanado;

    public Numero() {
        intentosActual = 1;
    }

    public Numero(Jugador jugador, Integer numeroAdivinar, Integer intentosMAX, Integer intentosActual, Boolean hasGanado) {
        this.jugador = jugador;
        this.numeroAdivinar = numeroAdivinar;
        this.intentosMAX = intentosMAX;
        this.intentosActual = intentosActual;
        this.hasGanado = hasGanado;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Integer getIntentosMAX() {
        return intentosMAX;
    }

    public void setIntentosMAX(Integer intentosMAX) {
        this.intentosMAX = intentosMAX;
    }

    public Integer getNumeroAdivinar() {
        return numeroAdivinar;
    }

    public void setNumeroAdivinar(Integer numeroAdivinar) {
        this.numeroAdivinar = numeroAdivinar;
    }

    public Integer getIntentosActual() {
        return intentosActual;
    }

    public void setIntentosActual(Integer intentosActual) {
        this.intentosActual = intentosActual;
    }

    public Boolean getHasGanado() {
        return hasGanado;
    }

    public void setHasGanado(Boolean hasGanado) {
        this.hasGanado = hasGanado;
    }

    @Override
    public String toString() {
        return "Numero{" +
                "jugador=" + jugador +
                ", numeroAdivinar=" + numeroAdivinar +
                ", intentosMAX=" + intentosMAX +
                ", intentosActual=" + intentosActual +
                ", hasGanado=" + hasGanado +
                '}';
    }
}
```

</td>
</tr>
</table>


#### Activity

##### ConfigActivity
Es la clase que pide al usuario un jugador y un número limite de intentos para adivinar. Comprueba que los campos no estén vacíos o tengan algún tipo de error.

```Java
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
```

##### PlayActivity
Clase que recibe el objeto numero que se ha inicializado en la clase ConfigActivity
Se pide al usuario un número en un EditText haciendo click en el Button comprueba si lo ha adivinado.
- Si falla, da un mensaje informativo de si el número que tiene que acertar es mayor o menor y muestra otro button para volver a intentarlo.
- Si acierta o llega al limite de intentos, cambia a la activity EndPlayActivity.

```Java
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
```

##### EndPlayActivity
Clase que recibe el objeto número de la clase PlayActivity.
- si ha acertado, muestra el número de intentos.
- si ha fallado, muestra el número final.

```Java
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
```