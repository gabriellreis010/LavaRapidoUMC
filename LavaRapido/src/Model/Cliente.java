package Model;

import java.util.ArrayList;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    private ArrayList<Veiculo> veiculos = new ArrayList<>();

    public Cliente(int id, String nome, String cpf,
                   String telefone, String email) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 /*adicionar veiculo ao cliente */
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }


    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

 /*remover veiculo atraves da placa */
    public void removerVeiculo(String placa) {

        Veiculo encontrado = null;

        for (Veiculo veiculo : veiculos) {

            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                encontrado = veiculo;
                break;
            }
        }

        if (encontrado != null) {
            veiculos.remove(encontrado);
        }
    }
}