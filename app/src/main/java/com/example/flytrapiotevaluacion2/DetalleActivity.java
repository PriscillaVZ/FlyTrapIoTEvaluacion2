
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

public class DetalleActivity extends AppCompatActivity {

    EditText etCodigoDetalle, etNivelDetalle, etObservacionesDetalle;
    Spinner spTipoDetalle;
    Button btnActualizar, btnEliminar;

    DBHelper dbHelper;
    int idTrampa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectar elementos
        etCodigoDetalle = findViewById(R.id.etCodigoDetalle);
        etNivelDetalle = findViewById(R.id.etNivelDetalle);
        etObservacionesDetalle = findViewById(R.id.etObservacionesDetalle);
        spTipoDetalle = findViewById(R.id.spTipoDetalle);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Conectar base de datos
        dbHelper = new DBHelper(this);

        // Configurar Spinner
        String[] tipos = {"McPhail", "Jackson", "Multilure"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tipos
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDetalle.setAdapter(adapter);

        // Recibir ID de la trampa
        idTrampa = getIntent().getIntExtra("id_trampa", -1);

        // Cargar datos
        cargarDatos();

        // Botón actualizar
        btnActualizar.setOnClickListener(v -> actualizarTrampa());

        // Botón eliminar
        btnEliminar.setOnClickListener(v -> eliminarTrampa());
    }

    // Cargar datos de la trampa
    private void cargarDatos() {

        if (idTrampa == -1) {
            Toast.makeText(this, "No se recibió el ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Trampa trampa = dbHelper.obtenerTrampaPorId(idTrampa);

        if (trampa != null) {

            etCodigoDetalle.setText(trampa.getCodigoSector());
            etNivelDetalle.setText(String.valueOf(trampa.getNivelCaptura()));
            etObservacionesDetalle.setText(trampa.getObservaciones());

            int posicion = 0;

            if (trampa.getTipoTrampa().equals("Jackson")) {
                posicion = 1;
            } else if (trampa.getTipoTrampa().equals("Multilure")) {
                posicion = 2;
            }

            spTipoDetalle.setSelection(posicion);

        } else {
            Toast.makeText(this, "Trampa no encontrada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Actualizar trampa
    private void actualizarTrampa() {

        String codigo = etCodigoDetalle.getText().toString().trim();
        String tipo = spTipoDetalle.getSelectedItem().toString();
        String nivelTexto = etNivelDetalle.getText().toString().trim();
        String observaciones = etObservacionesDetalle.getText().toString().trim();

        if (codigo.isEmpty() || nivelTexto.isEmpty()) {
            Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int nivel;

        try {
            nivel = Integer.parseInt(nivelTexto);
        } catch (NumberFormatException e) {
            etNivelDetalle.setError("Ingresa un número válido");
            return;
        }

        if (nivel < 0) {
            etNivelDetalle.setError("El nivel no puede ser negativo");
            return;
        }

        boolean resultado = dbHelper.actualizarTrampa(
                idTrampa, codigo, tipo, nivel, observaciones
        );

        if (resultado) {
            Toast.makeText(this, "Trampa actualizada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    // Eliminar trampa sin confirmación
    private void eliminarTrampa() {

        boolean resultado = dbHelper.eliminarTrampa(idTrampa);

        if (resultado) {
            Toast.makeText(this, "Trampa eliminada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
