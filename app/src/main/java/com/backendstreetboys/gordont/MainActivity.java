package com.backendstreetboys.gordont;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.backendstreetboys.gordont.database.*;
import com.backendstreetboys.gordont.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public AppDatabase bd;
    public UsuarioDao userDao;
    public PesoYAlturaDao pyaDao;

    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            setContentView(binding.getRoot());

        }

       */
    public static final String EL_IMC = "imc";
    public static final String EL_MENSAJE = "mensaje";
    public static final String EL_NOMBRE = "nombre";


    /*
     * hola javi
     * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Bundle extras = getIntent().getExtras();
//        double imc = extras.getDouble(EL_IMC);
//        String mensaje = extras.getString(EL_MENSAJE);
//        String nombre = extras.getString(EL_NOMBRE);
//        String texto_imc = String.valueOf(imc);

        recogerDatosBD();

        binding.ButtonDietasEjercicios.setOnClickListener(v -> {
            Log.d("MainActivity", "LLENGO A DIETAS EJERCICIOS");
          openDietasEjercicios();
          //  setContentView(R.layout.activity_dietas_ejercicios);
        });

        binding.ButtonModificarPerfil.setOnClickListener(v -> {
           openModificable();
           // setContentView(R.layout.activity_modificable);
        });



    }

    public void recogerDatosBD(){
        // Obtener objetos BD
        bd = AppDatabase.getDatabase(getApplicationContext());
        userDao = bd.userDao();
        pyaDao = bd.pyaDao();

        Usuario user = userDao.getAll().get(0);
        PesoYAltura pya = pyaDao.getLatest();

        List<PesoYAltura> historialPya = pyaDao.getAll();
        double imc = operacionIMC(pya.altura, pya.peso);
        String mensaje = resultadoMensaje(imc);
        String nombre = user.nombre;
        String texto_imc = String.valueOf(imc);

        binding.IMCtext.setText(texto_imc);
        binding.MENSAJEtext.setText(mensaje);
        binding.textUser.setText(nombre);
    }

    public void openModificable(){

        Intent intent = new Intent(this, Modificable.class);



        startActivity(intent);

    }

    public void openDietasEjercicios(){

        Intent intent = new Intent(this, DietasEjercicios.class);



        startActivity(intent);

    }

    public double operacionIMC(double doubleAltura, double doublePeso) {

        System.out.println(" El peso es de" + doublePeso);

        System.out.println("La altura es de " + doubleAltura);

        double alturaCuadrado = doubleAltura * doubleAltura;

        System.out.println("La altura ^2 es " + alturaCuadrado);

        double resultado = doublePeso / alturaCuadrado;

        double resultadoF = Math.round(resultado*100)/100;

        System.out.println("El resultado es de " + resultado);


        return resultadoF;

    }


    public String resultadoMensaje(double resultado) {
        String mensaje = null;

        if (resultado < 18.5) {
            mensaje = "Tu estas gordon't ";
            binding.MENSAJEtext.setTextColor(getResources().getColor(R.color.teal_200));
        } else if (resultado > 18.5 && resultado < 24.9) {
            mensaje = "Tu estas chad ";
            binding.MENSAJEtext.setTextColor(getResources().getColor(R.color.verde));
        } else if (resultado > 25.0 && resultado < 29.9) {
            mensaje = "Tu estas gordo ";
            binding.MENSAJEtext.setTextColor(getResources().getColor(R.color.amarillo));
        } else if (resultado > 30.0 && resultado < 39.9) {
            mensaje = "Tu estas MUY GORDO ";
            binding.MENSAJEtext.setTextColor(getResources().getColor(R.color.orange));
        } else if (resultado > 40.0) {
            mensaje = "no se como coño estas vivo ";
            binding.MENSAJEtext.setTextColor(getResources().getColor(R.color.rojo));
        }

        return mensaje;
    }


}