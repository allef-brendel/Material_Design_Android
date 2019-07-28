package com.example.macaxeiratec;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.macaxeiratec.adapters.Gravador;


public class SecondActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;

    private ImageView imageView;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private Button button2;
    private EditText editText;

    Gravador gravador;

    String[][] lista;

    Glide glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        gravador=new Gravador();
        lista=gravador.lerQuadrinhos();

        // Toolbar da tela de Detalhe dos quadrinhos
        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle(" Marvel Comics");
        mToolbar.setSubtitle(" Descrição Quadrinho");
        mToolbar.setLogo(R.drawable.marvel_simbolo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageVIewQuadrinho);

        textView = findViewById(R.id.textViewDescricao);
        textView2 = findViewById(R.id.precoSecond);
        textView3 = findViewById(R.id.quantPag);
        textView4 = findViewById(R.id.textTitulo);

        editText = findViewById(R.id.editTextQuantidade);

        button2 = findViewById(R.id.botaoCarrinho);

        Intent it = getIntent();
        int position = it.getIntExtra("position",-1);

        // Usando o Glide para mostrar as imagens na tela de detalhe do quadrinho, usando gravador para recuperar as URL's pegas do JSON
        glide.with(this).load(lista[position][5]+"/portrait_medium.jpg").into(imageView);

        textView4.setText(lista[position][0]);
        textView.setText(lista[position][1]);
        textView2.setText("R$ "+lista[position][2]);
        textView3.setText("Pag: "+lista[position][4]);

        // Botão de Adicionar ao carrinho
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = getIntent();
                int position = it.getIntExtra("position",-1);

                Intent intent = new Intent(SecondActivity.this, CarrinhoDeCompras.class);
                intent.putExtra("titulo", lista[position][0]);
                intent.putExtra("preco", lista[position][2]);

                intent.putExtra("quant", editText.getText().toString());
                startActivity(intent);
            }
        });

        // Toolbar parte de baixo da tela de detalhe do quadrinho
        mToolbarBottom = findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch(menuItem.getItemId()){
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("https://www.facebook.com/MarvelBR/?brand_redir=6883542487"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("https://www.youtube.com/user/MARVEL"));
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
                Toast.makeText(SecondActivity.this, "Botão Configurações Precionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // inflate dos itens da toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    // botao da seta de voltar da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }
}

