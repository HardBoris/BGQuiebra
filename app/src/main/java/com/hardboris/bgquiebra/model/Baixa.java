package com.hardboris.bgquiebra.model;

public class Baixa {
    private String codigo;
    private String baixa;
    private String descripcion;

    public Baixa(String codigo, String baixa, String descripcion) {
        this.codigo = codigo;
        this.baixa = baixa;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return codigo + " " + baixa;
    }
}
