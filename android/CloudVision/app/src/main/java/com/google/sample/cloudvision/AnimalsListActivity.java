package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.sample.cloudvision.dao.AnimalDao;
import com.google.sample.cloudvision.model.Animal;

import java.util.List;

public class AnimalsListActivity extends AppCompatActivity {

    private ListView listaAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_list);

        listaAnimals = (ListView) findViewById(R.id.lista_animal);

        listaAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long l) {
                Animal animal = (Animal) lista.getItemAtPosition(position);
                Intent intentVaiProFormulario = new Intent(AnimalsListActivity.this, FormActivity.class);
                intentVaiProFormulario.putExtra("animal", animal);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novoAnimal = (Button) findViewById(R.id.novo_animal);
        novoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(AnimalsListActivity.this, FormActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAnimals);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        AnimalDao dao = new AnimalDao(this);
        List<Animal> animals = dao.buscaAnimals();

        ArrayAdapter<Animal> adapter = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animals);
        listaAnimals.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Animal animal = (Animal) listaAnimals.getItemAtPosition(info.position);

                AnimalDao dao = new AnimalDao(AnimalsListActivity.this);
                dao.deleta(animal);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }
}
