package com.hardboris.bgquiebra.model;

public class Baixas {
    private String codigo;
    private String baixa;

    public Baixas(String codigo, String baixa) {
        this.codigo = codigo;
        this.baixa = baixa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBaixa() {
        return baixa;
    }

    public void setBaixa(String baixa) {
        this.baixa = baixa;
    }
}
