package com.example.myfirebase;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.CursorAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataBaseQuiz {

    //criar um outro nó raiz, dupla 001, campo nome, valor 100
    //referencia.child("usuarios").child("001").child("nome").setValue("100");

    private final DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();//Começa a referencia no nó raiz
    private DatabaseReference dbReferencia;
    DatabaseReference quizDados = referencia.child("quizDados");//cria nó quizDados

    List<Quiz> listaQuiz= new ArrayList<>();
    private List<Quiz> quizList;
    private QuizAdapter quizAdapter;

    private Quiz quizEscolhido;

    public void adicionar(Quiz quiz, int key){
        DatabaseReference quizDados = referencia.child("quizDados");

        quizDados.child(String.valueOf(key)).setValue(quiz);

    }

    public Quiz pegarQuiz(String key){
       // DatabaseReference quizPesquisa = referencia.child("quizDados");//refência é a chave
        final Quiz quiz= new Quiz();

        Query query = referencia.child("quizDados").orderByChild("id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Quiz quizData = dataSnapshot.getValue(Quiz.class);//Retorna info no molde da class quiz pelo firebase

//                quiz.setId(quizData.getId());//objeto quiz recebe do obj quizData firebase
//                quiz.setPergunta(quizData.getPergunta());
//                quiz.setResposta(quizData.getResposta());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Read Fail", "Error:quizPesquisa:pegarQuiz");
            }
        });
        return  quiz;
    }

    private void bancoDeDados(){

    }
    public List<Quiz> pegarTodosQuiz(){
        dbReferencia = FirebaseDatabase.getInstance().getReference();//Começa a referencia no nó raiz

        quizList = new ArrayList<>();

        dbReferencia.child("quizDados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaQuiz.clear();
                for(DataSnapshot obj:dataSnapshot.getChildren()){
                    Quiz quiz= obj.getValue(Quiz.class);
                    listaQuiz.add(quiz);
                }
                cu
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("listaa",""+listaQuiz);
        return  listaQuiz;
    }

    public void alterarQuiz(Quiz quiz){

    }
    public void deletarQuiz(Quiz quiz){

    }

    public int totalQuiz(){
        DatabaseReference totalQuiz = referencia.child("quizDados").child("totalQuiz");
        int total=0;

        Log.d("totale"," t "+totalQuiz.getKey());
        return total;
    }
}
