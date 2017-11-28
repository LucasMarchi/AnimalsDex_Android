package com.google.sample.cloudvision;

import android.widget.EditText;
import android.widget.RatingBar;

import com.google.sample.cloudvision.model.Animal;

/**
 * Created by Lucas Marchi on 12/11/2017.
 */

public class FormHelper {

    private final EditText campoNome;
    private final EditText campoDescricao;
    private final RatingBar campoNota;
    private Animal animal;

    public FormHelper(FormActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoDescricao = (EditText) activity.findViewById(R.id.formulario_descricao);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        animal = new Animal();
    }

    public Animal pegaAnimal(){
        animal.setNome(campoNome.getText().toString());
        animal.setDescricao(campoDescricao.getText().toString());
        animal.setNota(Double.valueOf(campoNota.getProgress()));
        return animal;
    }

    public void preencheFormulario(Animal animal) {
        campoNome.setText(animal.getNome());
        campoDescricao.setText(animal.getDescricao());
        campoNota.setProgress(animal.getNota().intValue());
        this.animal = animal;
    }
}
