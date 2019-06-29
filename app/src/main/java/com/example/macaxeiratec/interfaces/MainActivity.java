package com.example.macaxeiratec.interfaces;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.macaxeiratec.R;
import com.example.macaxeiratec.domain.Personagens;
import com.example.macaxeiratec.fragments.PersonagensFragments;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle("Konoha");
        mToolbar.setSubtitle("Descrição dos ninjas");
        mToolbar.setLogo(R.drawable.konoha_simbolo);
        setSupportActionBar(mToolbar);

        mToolbarBottom = findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch(menuItem.getItemId()){
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case R.id.action_google_plus:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case R.id.action_linkedin:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.linkedin.com"));
                        break;
                    case R.id.action_whatsapp:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                }
                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Botão Configurações Precionado", Toast.LENGTH_SHORT).show();
            }
        });

        //  FRAGMENT
        PersonagensFragments frag = (PersonagensFragments) getSupportFragmentManager().findFragmentByTag("person_frag");
        if (frag == null){
            frag = new PersonagensFragments();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container,frag,"person_frag");
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_second_activity){
            startActivity(new Intent(this, SecondActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Personagens> getSetPersonagensList(int quant){
        String[] nome = new String[]{"Naruto Uzumaki","Sasuke Uchiha","Sakura Haruno ","Kakashi Hatake","Shikamaru Nara","Sai","Ino Yamanaka","Maito Gai","Itachi Uchiha","Orochimaru","Rock Lee"};
        String[] clan = new String[]{"Uzumaki","Uchiha","Uchiha","Hatake","Nara","Yamanaka","Yamanaka","Desconhecido","Uchiha","Desconhecido","Lee"};
        int[] fotos = new int[]{R.drawable.naruto_foto,R.drawable.sasuke_foto,R.drawable.sakura_foto,R.drawable.kakashi_foto,R.drawable.shikamaru_foto,R.drawable.sai_foto,R.drawable.ino_foto,R.drawable.gai_foto,R.drawable.itachi_foto,R.drawable.orochimaru_foto,R.drawable.lee_foto};
        List<Personagens> listAux = new ArrayList<>();

        for (int i = 0; i < quant; i++){
            Personagens personagens = new Personagens(nome[i % nome.length], clan[i % clan.length], fotos[i % nome.length]);
            listAux.add(personagens);
        }
        return listAux;

    }

}

