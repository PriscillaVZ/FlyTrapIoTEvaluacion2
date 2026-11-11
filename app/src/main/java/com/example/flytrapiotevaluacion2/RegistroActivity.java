
package com.example.flytrapiotevaluacion2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    EditText etCodigo, etNivel, etObservaciones;
    Spinner spTipo;
    Button btnGuardar;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectar los elementos
        etCodigo = findViewById(R.id.etCodigo);
        etNivel = findViewById(R.id.etNivel);
        etObservaciones = findViewById(R.id.etObservaciones);
        spTipo = findViewById(R.id.spTipo);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Crear base de datos
        dbHelper = new DBHelper(this);

        // Opciones del Spinner
        String[] tipos = {"McPhail", "Jackson", "Multilure"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tipos
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTipo.setAdapter(adapter);

        // Boton guardar
        btnGuardar.setOnClickListener(v -> guardarTrampa());
    }

    // Metodo para guardar una trampa
    private void guardarTrampa() {

        String codigo = etCodigo.getText().toString().trim();
        String tipo = spTipo.getSelectedItem().toString();
        String nivelTexto = etNivel.getText().toString().trim();
        String observaciones = etObservaciones.getText().toString().trim();

        if (codigo.isEmpty() || nivelTexto.isEmpty()) {
            Toast.makeText(this, "Completa los campos obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int nivel;

        try {
            nivel = Integer.parseInt(nivelTexto);
        } catch (NumberFormatException e) {
            etNivel.setError("Ingresa un número válido");
            return;
        }

        if (nivel < 0) {
            etNivel.setError("El nivel no puede ser negativo");
            return;
        }

        // Guardar en la base de datos
        boolean resultado = dbHelper.insertarTrampa(
                codigo, tipo, nivel, observaciones
        );

        if (resultado) {
            Toast.makeText(this, "Trampa registrada correctamente",
                    Toast.LENGTH_SHORT).show();

            finish();

        } else {
            Toast.makeText(this, "Error al registrar la trampa",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
