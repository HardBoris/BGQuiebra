package com.hardboris.bgquiebra;

import static com.hardboris.bgquiebra.R.id.txtCod;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hardboris.bgquiebra.adapter.BaixaAdapter;
import com.hardboris.bgquiebra.model.Baixa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    EditText contrato, janela, parceira, servicio, portero, observaciones, tecnico;
    Button copiar, copiarObservaciones, nuevo;
    TextView mostraData, muestraDescripcion;
    RadioButton apartamento, casa, comercio;
    String isCasa, isComercio, isApartamento, isPortero, otro, codigo, motivo, descripcion;
    CharSequence luna;
    CheckBox temPortero;
    //Bundle recibeDatos = getIntent().getExtras();
    private Spinner spinner;
    private TextView txtCod, txtDescripcion;
    private ArrayList<Baixa> baixas;
    private BaixaAdapter baixaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date fecha =  new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        otro = sdf.format(fecha);
        mostraData = findViewById(R.id.tvFecha);
        mostraData.setText(otro);

        //codigos = findViewById(R.id.codigos_spinner);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.codigos_spinner,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codigos.setAdapter(adapter);*/

        copiar = findViewById(R.id.btnCopiar);
        copiarObservaciones = findViewById(R.id.btnCopiarObservaciones);
        nuevo = findViewById(R.id.btnNovo);
        contrato = findViewById(R.id.txtContrato);
        janela = findViewById(R.id.txtJanela);
        servicio = findViewById(R.id.txtServicio);
        tecnico = findViewById(R.id.txtTecnico);
        parceira = findViewById(R.id.txtParcera);
        apartamento = findViewById(R.id.rbApartamento);
        casa = findViewById(R.id.rbCasa);
        comercio = findViewById(R.id.rbComercio);
        temPortero = findViewById(R.id.chbPortero);
        portero = findViewById(R.id.txtPortero);
        observaciones = findViewById(R.id.txtObservaciones);
        luna = observaciones.getText().toString();
        muestraDescripcion = findViewById(R.id.txtDescripcion);

        spinner = findViewById(R.id.baixaSpinner);
        /*txtCod = findViewById(R.id.txtCod);
        txtDescripcion = findViewById(R.id.txtDescripcion);*/

        copiarObservaciones.setOnClickListener(v -> Observator());
        copiar.setOnClickListener(v -> Validator());
        nuevo.setOnClickListener(v -> Cleaner());

        temPortero.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (temPortero.isChecked()){
                temPortero.setChecked(true);
                portero.setEnabled(true);
            } else {
                temPortero.setChecked(false);
                portero.setEnabled(false);
            }
        });

        llenarLista();

        baixaAdapter = new BaixaAdapter(this, baixas);
        spinner.setAdapter(baixaAdapter);

        /*spinner.setOnItemSelectedListener(this);*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Baixa baixa = baixas.get(position);

                if (position == 0){
                    muestraDescripcion.setVisibility(View.GONE);
                }

                if (position != 0) {
                    codigo = baixa.getCodigo();
                    motivo = baixa.getBaixa();
                    muestraDescripcion.setVisibility(View.VISIBLE);
                    descripcion = baixa.getDescripcion();
                    muestraDescripcion.setText(descripcion);
                }

                Toast.makeText(MainActivity.this, baixa.getCodigo(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }



    private void Cleaner(){ //limpiar spinner
        contrato.setText("");
        janela.setText("");
        servicio.setText("");
        casa.setChecked(false);
        comercio.setChecked(false);
        apartamento.setChecked(false);
        temPortero.setChecked(false);
        portero.setText("");
        observaciones.setText("");
    }

    //guardar los valores de tecnico e parceira

    private void Observator(){
        if (observaciones.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "O campo das Observações está vazio!", Toast.LENGTH_SHORT).show();
        } else {
            ClipboardManager clipboardObservaciones = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipObservaciones = ClipData.newPlainText("obs",  "'" + observaciones.getText().toString() + "'" );
            clipboardObservaciones.setPrimaryClip(clipObservaciones);
            Toast.makeText(MainActivity.this, "Texto copiado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void Validator(){
        if (contrato.getText().toString().isEmpty()){
            contrato.setError("Este campo é obrigatório!");
        }/* else if (codigo.getText().toString().isEmpty()){
            codigo.setError("Este compo é obrigatório!");
        } else if (motivo.getText().toString().isEmpty()){
            motivo.setError("Este campo é obrigatório!");
        } */else if (janela.getText().toString().isEmpty()){
            janela.setError("Este campo é obrigatório!");
        } else if (servicio.getText().toString().isEmpty()){
            servicio.setError("Este campo é obrigatório!");
        } else if (observaciones.getText().toString().isEmpty()){
            observaciones.setError("Este campo é obrigatório!");
        } else {
            Transferir();
        }
    }

    private void llenarLista(){
        baixas = new ArrayList<>();
        baixas.add(new Baixa("", "--Selecione o motivo da baixa--", ""));
        baixas.add(new Baixa("100", "Agendamento Não Cumprido", "Quando o técnico não consegue cumprir o período agendado com o cliente"));
        baixas.add(new Baixa("101", "Endereço Não Localizado", "Quando o técnico não localiza o endereço para executar o serviço"));
        baixas.add(new Baixa("102", "Área / Situação de Risco", "Quando o técnico vai até a residência do cliente e identifica que o serviço não podera ser realizado devido à chuva."));
        baixas.add(new Baixa("103", "Chuva", "Quando o técnico vai até a residência do cliente e identifica que o serviço não podera ser realizado devido à chuva."));
        /*baixas.add(new Baixa(104, "Falta de Material", "Quando o técnico vai até a residência do cliente e não consegue executar o serviço, devido à falta de material ou equipamento."));
        baixas.add(new Baixa(105, "Sem Energia Elétrica Na Região / Prédio / Residência"));
        baixas.add(new Baixa(106, "Cliente Ausente"));
        baixas.add(new Baixa(107, "Entrada Não Autorizada"));
        baixas.add(new Baixa(108, "Tubulação Obstruída Sem Solução"));
        baixas.add(new Baixa(110, "Problema Na Tubulação"));
        baixas.add(new Baixa(112, "Sem Acesso ao DG / Sotão"));
        baixas.add(new Baixa(113, "Sem TV / Computador na Residência / Celular"));
        baixas.add(new Baixa(120, "Impossibilidade ao Atendimento"));
        baixas.add(new Baixa(125, "Cliente Desiste da Agenda"));
        baixas.add(new Baixa(202, "Node em Outage"));
        baixas.add(new Baixa(203, "Rede Externa Com Problema"));
        baixas.add(new Baixa(204, "Backbone Com Problema"));
        baixas.add(new Baixa(205, "Falta Tap Ou Passivos / Tap Ou Passivo Lotado"));
        baixas.add(new Baixa(206, "Prédio Sem Backbone"));
        baixas.add(new Baixa(207, "Prédio Sem Retorno Ativado"));*/
    }

    private void Transferir(){
        if (casa.isChecked()){
            isCasa = "Sim";
            isComercio = "Não";
            isApartamento = "Não";
        } else if (comercio.isChecked()){
            isCasa = "Não";
            isComercio = "Sim";
            isApartamento = "Não";
        } else if (apartamento.isChecked()){
            isCasa = "Não";
            isComercio = "Não";
            isApartamento = "Sim";
        }

        if (temPortero.isChecked()){
            isPortero = "Sim";
        } else {
            isPortero = "Não";
            portero.setText("N/A");
        }

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("abc",
                "❌️ *VALIDAÇÃO DE QUEBRA* ❌️\n" +
                "*" + otro + "*" +
                "\nContrato: *884/" + contrato.getText().toString() + "*" +
                "\nCódigo: *" + codigo + "*" +
                "\nMotivo: *" + motivo + "*" +
                "\nJanela: *" + janela.getText().toString() + "*" +
                "\nServiço: *" + servicio.getText().toString() + "*" +
                "\nTécnico: *" + tecnico.getText().toString() + "*" +
                "\nParceira: *" + parceira.getText().toString() + "*" +
                "\nCasa: *" + isCasa + "*" +
                "\nComercio: *" + isComercio + "*" +
                "\nApartamento: *" + isApartamento + "*" +
                "\nTem Porteiro: *" + isPortero + "*" +
                "\nPorteiro: *" + portero.getText().toString() + "*" +
                "\nObservações: *" + observaciones.getText().toString()+ "*");
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(MainActivity.this, "copiado...", Toast.LENGTH_SHORT).show();
    }
}