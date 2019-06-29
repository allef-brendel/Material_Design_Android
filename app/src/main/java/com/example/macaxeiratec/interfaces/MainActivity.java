package com.example.macaxeiratec.interfaces;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.example.macaxeiratec.R;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private Drawer navigationDrawerLeft;
    private Drawer navigationDrawerRight;
    private AccountHeader headerNavigationLeft;
    private int mPositionClicked;

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

                switch (menuItem.getItemId()) {
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
        if (frag == null) {
            frag = new PersonagensFragments();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "person_frag");
            ft.commit();
        }

        //HEADER
        headerNavigationLeft = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                //.withProfileImagesVisible(false)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.clan_uzumaki)
                .addProfiles(
                        new ProfileDrawerItem().withName("Kakashi").withEmail("kakashi@gmail.com").withIcon(getResources().getDrawable(R.drawable.kakashi_foto)),
                        new ProfileDrawerItem().withName("Naruto").withEmail("naruto@gmail.com").withIcon(getResources().getDrawable(R.drawable.naruto_foto)),
                        new ProfileDrawerItem().withName("Sakura").withEmail("sakura@gmail.com").withIcon(getResources().getDrawable(R.drawable.sakura_foto)),
                        new ProfileDrawerItem().withName("Sasuke").withEmail("sasuke@gmail.com").withIcon(getResources().getDrawable(R.drawable.sasuke_foto))
                     )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Toast.makeText(MainActivity.this, "onProfileChanged: "+profile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.clan_uchiha);
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
                       /*for(int cont = 0, tam = navigationDrawerLeft.getDrawerItems().size(); cont < tam; cont++){
                            if (cont == mPositionClicked && mPositionClicked <= 3 ){
                                PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(cont);
                                aux.withIcon(getResources().getDrawable(getCorrectDrawerIcon(cont,false)));
                                break;
                            }
                        }

                        if(position <= 3){
                            ((PrimaryDrawerItem)drawerItem).withIcon(getResources().getDrawable(getCorrectDrawerIcon(position,true)));
                        }
                            mPositionClicked = position;
                            navigationDrawerLeft.getAdapter().notifyAdapterDataSetChanged();*/
                            return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();



        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Vila da Folha").withIcon(getResources().getDrawable(R.drawable.car_selected_1)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Vila da Areia").withIcon(getResources().getDrawable(R.drawable.car_selected_2)));
        navigationDrawerLeft.addItem(new DividerDrawerItem());
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Vila da Névoa").withIcon(getResources().getDrawable(R.drawable.car_selected_3)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Vila da Chuva").withIcon(getResources().getDrawable(R.drawable.car_selected_4)));
        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Configurações").withTextColor((getResources().getColor(R.color.colorPrimarytext))));
        navigationDrawerLeft.addItem(new SwitchDrawerItem().withName("Notificação").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener));
        navigationDrawerLeft.addItem(new ToggleDrawerItem().withName("News").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener));

        //END - RIGHT
        /*navigationDrawerRight = new DrawerBuilder()
                .withActivity(this)
                //.withToolbar(mToolbar)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.END)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(MainActivity.this, "onItemClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();*/

    }

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

