/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Host;

/**
 *
 * @author Rafael Macedo
 */
public class Arquivo {
    
    private String nome;
    private String tamanho;
    
    public Arquivo(String nome, String tamanho){
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    
    
}
