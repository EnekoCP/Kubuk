package com.example.kubuk.ListaCompra;

public class Elemento {
    private Boolean checked ;
    private String itemText = "";
    private String email;

    public Elemento(Boolean check, String text){
        this.checked=check;
        this.itemText=text;
    }

    public Boolean isChecked() {
        return checked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

}
