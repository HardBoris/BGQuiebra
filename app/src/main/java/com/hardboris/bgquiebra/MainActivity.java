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
        baixas.add(new Baixa("102", "Área / Situação de Risco", "Quando o técnico vai até a residência do cliente e identifica situação de risco permanente ou momentâneo à sua integridade física."));
        baixas.add(new Baixa("103", "Chuva", "Quando o técnico vai até a residência do cliente e identifica que o serviço não podera ser realizado devido à chuva."));
        baixas.add(new Baixa("104",
                "Falta de Material",
                "Quando o técnico vai até a residência do cliente e não consegue executar o serviço, devido à falta de material ou equipamento."));
        baixas.add(new Baixa("105",
                "Sem Energia Elétrica Na Região / Prédio / Residência",
                "Quando o técnico vai até a residência do cliente e não consegue executar o serviço devido à falta de energia elétrica."));
        baixas.add(new Baixa("106",
                "Cliente Ausente",
                "Quando o técnico vai até a residência do cliente e o mesmo ou os responsáveis estão ausentes"));
        baixas.add(new Baixa("107",
                "Entrada Não Autorizada",
                "Quando o técnico vai até a residência e não obtém autorização do responsável para sua entrada."));
        baixas.add(new Baixa("108",
                "Tubulação Obstruída sem solução",
                "Quando o técnico vai até a residência do cliente e detecta que o mesmo tem de fazer algum tipo de obra civil ou alteração na rede elétrica em sua residência para viabilizar a execução do serviço."));
        baixas.add(new Baixa("110",
                "Problema Na Tubulação",
                "Quando o técnico vai até a residência do cliente e detecta algum problema na tubulação e o cliente resolverá o problema para viabilizar a execução do serviço."));
        baixas.add(new Baixa("112",
                "Sem Acesso Ao DG / Sótão",
                "Quando o técnico vai até a residência do cliente e não consegue acesso ao DG ou Sótão para executar o serviço"));
        baixas.add(new Baixa("113",
                "Sem TV / Computador na Residencia / Celular",
                "Quando o técnico vai até a residência do cliente e constata que o mesmo não possui os equipamentos, necessários para que se conclua o serviço."));
        baixas.add(new Baixa("120",
                "Impossibilidade ao Atendimento",
                "Quando o técnico fica impedido de realizar a visita"));
        baixas.add(new Baixa("125",
                "Cliente desiste da Agenda",
                "Quando o técnico vai até a residência do cliente e o mesmo ou o responsável solicita novo agendamento"));
        baixas.add(new Baixa("202",
                "Node em Outage",
                "Quando o Outage impede a execução dos serviços em campo ou são geradas visitas indevidas relacionadas a este problema"));
        baixas.add(new Baixa("203",
                "Rede Externa Com Problema",
                "Quando o técnico vai até a residência do cliente e detecta que só conseguirá executar o serviço após uma intervenção na Rede Externa"));
        baixas.add(new Baixa("204",
                "Backbone Com Problema",
                "Quando o técnico vai até a residência do cliente e detecta que só conseguirá executar o serviço após uma intervenção no Backbone"));
        baixas.add(new Baixa("205",
                "Falta Tap Ou Passivo / Tap Ou Passivo Lotado",
                "Quando o técnico vai até a residência do cliente e detecta que o Tap ou Passivo que deveria estar disponível para o HP não existe ou está lotado, impedindo a execução do serviço."));
        baixas.add(new Baixa("206",
                "Prédio Sem Backbone",
                "Quando o técnico vai até a residência do cliente e detecta que o MDU no qual o mesmo reside não possui Backbone, impedindo a execução do serviço."));
        baixas.add(new Baixa("207",
                "Prédio Sem Retorno Ativado",
                "Quando o técnico vai até a residência do cliente e detecta que o Backbone não possui retorno ativado, impedindo a execução do serviço."));
        baixas.add(new Baixa("208",
                "Problemas no NOW",
                "Quando técnico vai a residência do cliente e identifica problema do NOW e necessita de intervenção do NOC"));
        baixas.add(new Baixa("209",
                "Prédio sem NAP GPON",
                "Quando o prédio não contempla de estrutura interna para instalação GPON"));
        baixas.add(new Baixa("211",
                "Falta NAP/NAP Lotada",
                "Quando o técnico vai até a residência do cliente e detecta que a NAP que deveria estar disponível para o HP não existe ou está lotado, impedindo a execução do serviço."));
        baixas.add(new Baixa("217",
                "Backbone GPON com problema",
                "Quando o técnico vai até a residência do cliente e detecta que só conseguirá executar o serviço após uma intervenção no Backbone GPON"));
        baixas.add(new Baixa("301",
                "Tipo de OS Incorreta",
                "Quando o técnico constata que o operador utilizou o tipo de OS incorreta ao do serviço solicitado"));
        baixas.add(new Baixa("302",
                "Desistência da Assinatura / Serviço",
                "Quando o cliente desiste do pedido da assinatura ou serviço. Este código também deve ser utilizado para baixa do backlog para instalação, seviços e manutenção."));
        baixas.add(new Baixa("303",
                "Forma de Instalação Não Aceita Pelo Cliente",
                "Quando o técnico vai até a residência do cliente e o mesmo não aceita a forma de instalação possível."));
        baixas.add(new Baixa("304",
                "Node Nao Liberado (Para Virtua e/ou Net Fone)",
                "Quando o técnico vai até a residência do cliente e detecta a impossibilidade de executar o serviço, pois o Node onde está localizado o seu HP não está liberado para o Vírtua e/ou Net Fone"));
        baixas.add(new Baixa("305",
                "Rua não cabeada",
                "Para clientes que solicitaram produtos compatíveis com a rede HFC, e você identificar que não existe a nossa rede HFC no local"));
        baixas.add(new Baixa("306",
                "Não Reside No Endereço",
                "Quando o técnico vai até a residência do cliente e detecta a impossibilidade de executar o serviço, pois o mesmo não reside naquele endereço."));
        baixas.add(new Baixa("307",
                "Residência Em Construção /Reforma",
                "Quando o técnico vai até a residência do cliente e detecta a impossibilidade de executar o serviço, pois a mesmaestá em construção ou reforma."));
        baixas.add(new Baixa("308",
                "Instalação Não Contemplara Padrão",
                "Quando o técnico vai até a residência do cliente e detecta a impossibilidade de executar o serviço, pois irá ferir os padrões técnicos determinados pela Claro."));
        baixas.add(new Baixa("309",
                "Computador não possui configuração Minima",
                "Quando o técnico vai até a residênciado cliente e detecta a impossibilidadede executar o serviço, pois seu computador não atende aos pré-requisitos técnicos necessários."));
        baixas.add(new Baixa("312",
                "Cliente não solicitou serviço",
                "Quando o técnico vai até a residência do cliente e o mesmo afirma não ter solicitado qualquer tipo de serviço."));
        baixas.add(new Baixa("316",
                "Rua não cabeada GPON.",
                "Para clientes que solicitaram produtos compatíveis com a rede GPON, e você identificar que não existe a nossa rede GPON no local."));
        baixas.add(new Baixa("400",
                "Correção de Cadastro",
                "Quando o COP necessita corrigir um contrato através da execução de uma OS com ou sem o técnico ir a campo."));
        baixas.add(new Baixa("402",
                "Divergência de Dados Cadastrais",
                "Quando algum dado cadastral do cliente precisa ser atualizado"));
        baixas.add(new Baixa("408",
                "Equipamento do Cliente com Defeito",
                "Quando o técnico vai até a residência do cliente e detecta que o problema está no equipamento ou na rede de dados instalada pelo cliente."));

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