
package com.example.flytrapiotevaluacion2;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;
    RecyclerView recyclerViewTrampas;

    DBHelper dbHelper;
    TrampaAdapter adapter;
    ArrayList<Trampa> listaTrampas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectar boton agregar
        btnAgregar = findViewById(R.id.btnAgregar);

        // Abrir formulario de registro
        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Conectar lista de trampas
        recyclerViewTrampas = findViewById(R.id.recyclerViewTrampas);
        recyclerViewTrampas.setLayoutManager(new LinearLayoutManager(this));

        // Conectar base de datos
        dbHelper = new DBHelper(this);

        // Mostrar registros
        cargarTrampas();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dbHelper != null) {
            cargarTrampas();
        }
    }

    private void cargarTrampas() {
        listaTrampas = new ArrayList<>(dbHelper.obtenerTrampas());

        adapter = new TrampaAdapter(listaTrampas, trampa -> {

            // Abrir pantalla de detalle
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);

            // Enviar ID de la trampa seleccionada
            intent.putExtra("id_trampa", trampa.getId());

            startActivity(intent);

        });

        recyclerViewTrampas.setAdapter(adapter);
    }
}
