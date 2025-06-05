package com.hardboris.bgquiebra;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText contrato, codigo, motivo, janela, parceira, servicio, portero, observaciones, tecnico;
    Button copiar;
    TextView mostraData;
    RadioButton apartamento, casa, comercio;
    String isCasa, isComercio, isApartamento, isPortero, otro;
    CheckBox temPortero;
    //Bundle recibeDatos = getIntent().getExtras();


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

        //tecnico = recibeDatos.getString("d1");
        //parcera = recibeDatos.getString("d2");
        contrato = findViewById(R.id.txtContrato);
        codigo = findViewById(R.id.txtCodigo);
        motivo = findViewById(R.id.txtMotivo);
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

        copiar.setOnClickListener(v -> Validator());

        temPortero.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (temPortero.isChecked()){
                temPortero.setChecked(true);
                portero.setEnabled(true);
            } else {
                temPortero.setChecked(false);
                portero.setEnabled(false);
            }
        });



    }

    private void Cleaner(){
        contrato.setText("");
        codigo.setText("");
        motivo.setText("");
        janela.setText("");
        servicio.setText("");
        casa.setChecked(false);
        comercio.setChecked(false);
        apartamento.setChecked(false);
        temPortero.setChecked(false);
        portero.setText("");
        observaciones.setText("");
    }

    private void Validator(){
        if (contrato.getText().toString().isEmpty()){
            contrato.setError("Este campo é obrigatório!");
        } else if (codigo.getText().toString().isEmpty()){
            codigo.setError("Este compo é obrigatório!");
        } else if (motivo.getText().toString().isEmpty()){
            motivo.setError("Este campo é obrigatório!");
        } else if (janela.getText().toString().isEmpty()){
            janela.setError("Este campo é obrigatório!");
        } else if (servicio.getText().toString().isEmpty()){
            servicio.setError("Este campo é obrigatório!");
        } else if (observaciones.getText().toString().isEmpty()){
            observaciones.setError("Este campo é obrigatório!");
        } else {
            Transferir();
        }
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
                "\nCódigo: *" + codigo.getText().toString() + "*" +
                "\nMotivo: *" + motivo.getText().toString() + "*" +
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
        Cleaner();
    }
}