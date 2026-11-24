package Controller;

import Model.Agendamento;
import Model.Veiculo;
import Model.Cliente;
import java.util.ArrayList;

public class ControleVeiculo {

    private ArrayList<Veiculo> veiculos = new ArrayList<>();

   /*metodo para cadastrar veiculo e vincular ao cliente */
    public boolean cadastrarVeiculo(Veiculo veiculo, Cliente cliente) {

        if (cliente == null || veiculo == null) {
            return false;
        }

        if (veiculo.getCarro() == null ||
                veiculo.getCarro().trim().isEmpty() ||
                veiculo.getPlaca() == null ||
                veiculo.getPlaca().trim().isEmpty()) {

            return false;
        }

        if (buscarVeiculo(veiculo.getPlaca()) != null) {
            return false;
        }

        veiculos.add(veiculo);
        cliente.getVeiculos().add(veiculo);

        return true;
    }


    public void listarVeiculos() {

        if (veiculos.isEmpty()) {

            System.out.println("Nenhum veículo cadastrado");
            return;
        }

        for (Veiculo veiculo : veiculos) {

            System.out.println("Carro: " + veiculo.getCarro());
            System.out.println("Placa: " + veiculo.getPlaca());
        }
    }

 /*buscar veiculo pela placa*/
    public Veiculo buscarVeiculo(String placa) {

        for (Veiculo veiculo : veiculos) {

            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {

                return veiculo;
            }
        }

        return null;
    }


    public boolean possuiAgendamentos(String placa,
            controleAgendamento controleAgendamento) {

        for (Agendamento agendamento :
                controleAgendamento.getAgendamentos()) {

            if (agendamento.getVeiculo().getPlaca()
                    .equalsIgnoreCase(placa)) {

                return true;
            }
        }

        return false;
    }

 /*excluir veiculo*/
    public boolean excluirVeiculo(String placa, Cliente cliente,
            controleAgendamento controleAgendamento) {

        Veiculo veiculo = buscarVeiculo(placa);

        if (veiculo == null || cliente == null) {
            return false;
        }

        
        if (!cliente.getVeiculos().contains(veiculo)) {
            return false;
        }

        
        if (possuiAgendamentos(placa, controleAgendamento)) {
            return false;
        }

       
        
        veiculos.remove(veiculo);
        cliente.getVeiculos().remove(veiculo);

        return true;
    }

    
    
    public ArrayList<Veiculo> getVeiculos() {

        return veiculos;
    }
}