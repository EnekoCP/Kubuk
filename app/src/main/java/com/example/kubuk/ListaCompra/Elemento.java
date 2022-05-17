package com.example.kubuk.ListaCompra;

public class Elemento {
    private String checked = "";
    private String itemText = "";

    public Elemento(String check, String text){
        this.checked=check;
        this.itemText=text;
    }

    public String isChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

}
