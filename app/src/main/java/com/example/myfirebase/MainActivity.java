package com.example.myfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();//Começa a referencia no nó raiz
    TextView pergunta, textKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences editarTotal = getSharedPreferences("MyFireBase", Context.MODE_PRIVATE);
        int total= editarTotal.getInt("totalQuiz",0);// total de elementos e última posição usada

        final DataBaseQuiz dbQuiz= new DataBaseQuiz();

        textKey = findViewById(R.id.textKey);
        //criar um outro nó raiz, dupla 001, campo nome, valor 100
        //referencia.child("usuarios").child("001").child("nome").setValue("100");

        DatabaseReference quizDados = referencia.child("quizDados");

        // dbQuiz.pegarQuiz("-MA-4vEY5e1Uo_Dms36w");

//EXIBIR
        List<Quiz> listaQuiz= dbQuiz.pegarTodosQuiz();
        String listaquiz= "";
        for (Quiz q: listaQuiz) {
            String log= "Id: "+q.getId()+" | "+q.getPergunta()+" | "+q.getResposta()+"\n";
            listaquiz = listaquiz+ log;
        }

        textKey.setText(listaquiz);

        //Ordenar e depois limitar pesquisa
        Query quizPesquisaKey = quizDados.orderByKey();

        quizPesquisaKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());
                String key= dataSnapshot.getKey();
                String dadoKey= "";
                int contagem = 1;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String keyy= dataSnapshot.getKey();
                    if(contagem == 2) {
                       // dadoKey = "posição 2" + child.getKey();
                        break;
                    }
                    contagem ++;
                }
                textKey.setText("Id: "+dadoKey);
                Log.d("key","key "+dadoKey);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Read Fail", "Error:quizPesquisaKey");
            }
        });

        Quiz quiz= new Quiz("pergunta 3","C");
       // dbQuiz.salvarId(quize);
        salvar(quiz, dbQuiz, total, editarTotal);
        int n =dbQuiz.totalQuiz();
        exibirDados(quizDados);
        exibirQuiz();
        //editar(quiz, quizDados);
        pesquisarQuiz(quizDados);


    }
    private void salvar(Quiz quiz, DataBaseQuiz dbQuiz, int total, SharedPreferences editarTotal) {
        total= total+1;//última posição + o próximo salvamento
        quiz.setId(total);//sua posição é seu Id

        dbQuiz.adicionar(quiz, total);
        editarTotal(total, editarTotal);
    }

    private void editarTotal(int total, SharedPreferences editarTotal){

        SharedPreferences.Editor editor = editarTotal.edit();
        editor.putInt("totalQuiz", total);
        editor.commit();

    }

     private void exibirQuiz() {
        DatabaseReference quizPesquisa = referencia.child("quizDados").child("-MA2v1HOrQd5hHrttbt5");

        quizPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("quiz","quiz: "+dataSnapshot.getValue()); //todas as info
                Quiz quiz = dataSnapshot.getValue(Quiz.class);
//                Log.d("quizz","texto : "+ quiz.getPergunta());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Read Fail", "Error:quizPesquisa:exibirQuiz");
            }
        });
    }

    private void pesquisarQuiz(DatabaseReference quizDados) {
        Query quizPesquisa= quizDados.orderByChild("pergunta").equalTo("Texto Pergunta");

        quizPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Read Fail", "Error:quizPesquisa:pesquisarQuiz");
            }
        });
        //limitar pesquisa
        //Query quizPesquisaNome = quizDados.orderByKey().limitToFirst(2);
    }

    private void exibirDados(DatabaseReference quizDados) {

        quizDados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             //   Log.i("exibirTodos", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Read Fail", "Error:quizDados:exibirDados");
            }
        });

    }
    private void editar(Quiz quiz, DatabaseReference quizDados) {

        Quiz quizEdit= new Quiz("Texto pergunta edit","C");

        quizDados.push().setValue(quizEdit);
    }

}
