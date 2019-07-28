package com.example.macaxeiratec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.macaxeiratec.adapters.Gravador;
import com.example.macaxeiratec.domain.Personagens;
import com.example.macaxeiratec.fragments.PersonagensFragments;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Drawer navigationDrawerLeft;
    private AccountHeader headerNavigationLeft;

    private String[][] listaDados;
    private Gravador gravador;

    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            Toast.makeText(MainActivity.this, "onCheckedChanged "+(isChecked ? "true" : "false"), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        criarPastaFile();
        gravador=new Gravador();

        listaDados = recuperarLista();
        criarPastaFile();

        // Gravador para salvar informaçoes dos quadrinhos
        gravador.salvarInfoQuadrinhos(listaDados);
        gravador.salvarQuantItens(listaDados.length);

        // Toolbar da tela de listagem
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle("    Marvel Comics");
        mToolbar.setSubtitle("    Quadrinhos Marvel");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);

        //  Fragment
        PersonagensFragments frag = (PersonagensFragments) getSupportFragmentManager().findFragmentByTag("person_frag");
        if (frag == null) {
            frag = new PersonagensFragments();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "person_frag");
            ft.commit();
        }

        //Header do navigation Drawer
        headerNavigationLeft = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                //.withProfileImagesVisible(false)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.homemdeferro_capa)
                .addProfiles(
                        new ProfileDrawerItem().withName("Homem de Ferro").withEmail("homemdeferro@gmail.com").withIcon(getResources().getDrawable(R.drawable.homemdeferro)),
                        new ProfileDrawerItem().withName("Thor").withEmail("thor@gmail.com").withIcon(getResources().getDrawable(R.drawable.thor)),
                        new ProfileDrawerItem().withName("Capitão America").withEmail("capitaoamerica@gmail.com").withIcon(getResources().getDrawable(R.drawable.capitao_america)),
                        new ProfileDrawerItem().withName("Hulk").withEmail("hulk@gmail.com").withIcon(getResources().getDrawable(R.drawable.hulk))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Toast.makeText(MainActivity.this, "onProfileChanged: "+profile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.capitao_america_capa);
                        return false;
                    }
                })
                .build();

        //NAVIGATION DRAWER
        navigationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withSliderBackgroundColor(getResources().getColor(R.color.colorCardViwe))
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(
                        new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Todos Quadrinhos").withIcon(getResources().getDrawable(R.drawable.car_selected_4)));
        navigationDrawerLeft.addItem(new DividerDrawerItem());
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Quadrinhos Normais").withIcon(getResources().getDrawable(R.drawable.car_selected_2)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Quadrinhos Raros").withIcon(getResources().getDrawable(R.drawable.car_selected_3)));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Configurações").withTextColor((getResources().getColor(R.color.colorPrimarytext))));
        navigationDrawerLeft.addItem(new SwitchDrawerItem().withName("Notificação").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener));
        navigationDrawerLeft.addItem(new ToggleDrawerItem().withName("News").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener));

    }

    // Metodo que manda os dados para o Adapter
    public List<Personagens> getSetPersonagensList(int quant){

        String[] title = new String[listaDados.length];
        String[] descricao = new String[listaDados.length];
        String[] quantPaginas = new String[listaDados.length];
        String[] precos = new String[listaDados.length];
        int[] fotos = new int[]{R.drawable.quadrinho1,R.drawable.quadrinho2,R.drawable.quadrinho3,R.drawable.quadrinho4,R.drawable.quadrinho5,R.drawable.quadrinho6,R.drawable.quadrinho7,R.drawable.quadrinho8,R.drawable.quadrinho9,R.drawable.quadrinho10,R.drawable.quadrinho11,R.drawable.quadrinho12,R.drawable.quadrinho13,R.drawable.quadrinho14,R.drawable.quadrinho15,R.drawable.quadrinho16,R.drawable.quadrinho17,R.drawable.quadrinho18,R.drawable.quadrinho19,R.drawable.quadrinho20};

        for (int i = 0; i<listaDados.length; i++){
            title[i] = listaDados[i][0];
            descricao[i] = listaDados[i][1];
            precos[i] = listaDados[i][2];
            quantPaginas[i] = listaDados[i][4];

        }
        List<Personagens> listAux = new ArrayList<>();

        for (int i = 0; i < quant; i++){
            Personagens personagens = new Personagens(listaDados[i][0], listaDados[i][2], fotos[i]);
            listAux.add(personagens);
        }
        return listAux;
    }

    // Metodo para escolher o icone certo do drawer
    private int getCorrectDrawerIcon(int position, boolean isSelected){
        switch (position){
            case 0:
                return ( isSelected ? R.drawable.car_selected_1 : R.drawable.car_1 );
            case 1:
                return ( isSelected ? R.drawable.car_selected_2 : R.drawable.car_2 );
            case 2:
                return ( isSelected ? R.drawable.car_selected_3 : R.drawable.car_3 );
            case 3:
                return ( isSelected ? R.drawable.car_selected_4 : R.drawable.car_4 );
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            Toast.makeText(this, "Botão Configurações Precionado", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    // Metodo para recuperar dados de CarregarDadosJSON
    private String[][] recuperarLista(){
        String[][] lista=new String[20][6];
        for (int x=0;x<lista.length;x++){
            lista[x][0]=getIntent().getStringExtra("title"+x);
            lista[x][1]=getIntent().getStringExtra("descrição"+x);
            lista[x][2]=getIntent().getStringExtra("preço"+x);
            lista[x][3]=getIntent().getStringExtra("id"+x);
            lista[x][4]=getIntent().getStringExtra("pag"+x);
            lista[x][5]=getIntent().getStringExtra("thumbnails"+x);
        }

        return lista;
    }

    // Cria o diretorio file
    public void criarPastaFile(){
        try {
            FileOutputStream file=openFileOutput("teste.txt",MODE_PRIVATE);
            String s="asd";
            file.write(s.getBytes());
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

