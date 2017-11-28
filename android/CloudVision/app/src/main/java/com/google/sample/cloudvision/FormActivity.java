package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.sample.cloudvision.dao.AnimalDao;
import com.google.sample.cloudvision.model.Animal;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        helper = new FormHelper(this);

        Intent intent = getIntent();
        Animal animal = (Animal) intent.getSerializableExtra("animal");
        if(animal != null){
            helper.preencheFormulario(animal);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Animal animal = helper.pegaAnimal();
                AnimalDao dao = new AnimalDao(this);
                if (animal.getId() != null){
                    dao.altera(animal);
                } else {
                    dao.insere(animal);
                }
                dao.close();
                Toast.makeText(FormActivity.this, "Animal " + animal.getNome() + " com sucesso!", Toast.LENGTH_LONG).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
