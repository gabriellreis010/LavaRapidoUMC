package Controller;

import Model.Status;
import Model.Agendamento;
import Model.Veiculo;
import Model.Servico;
import Model.Cliente;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class controleAgendamento {

    private ArrayList<Agendamento> agendamentos = new ArrayList<>();

 
    public boolean agendarServico(Agendamento agendamento) {

        if (agendamento == null) {
            return false;
        }

        if (buscarAgendamento(agendamento.getId()) != null) {
            return false;
        }

        if (agendamento.getCliente() == null ||
                agendamento.getVeiculo() == null ||
                agendamento.getServico() == null) {

            return false;
        }

        if (!agendamento.getCliente().getVeiculos()
                .contains(agendamento.getVeiculo())) {

            return false;
        }

        if (agendamento.getDataHora() == null ||
                !verificarHorario(agendamento.getDataHora())) {

            return false;
        }

        agendamento.setStatus(Status.AGENDADO);
        agendamentos.add(agendamento);

        return true;
    }


    public void listarAgendamentos() {

        if (agendamentos.isEmpty()) {

            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        for (Agendamento agendamento : agendamentos) {

            System.out.println("ID: " + agendamento.getId());
            System.out.println("Cliente: " + agendamento.getCliente().getNome());
            System.out.println("Serviço: " + agendamento.getServico().getServico());
            System.out.println("Data: " + agendamento.getDataHora());
            System.out.println("Status: " + agendamento.getStatus());
            System.out.println("Placa: " + agendamento.getVeiculo().getPlaca());
        }
    }


    public Agendamento buscarAgendamento(int id) {

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getId() == id) {
                return agendamento;
            }
        }

        return null;
    }


    public boolean cancelarAgendamento(int id) {

        Agendamento agendamento = buscarAgendamento(id);

        if (agendamento == null) {
            return false;
        }

        if (agendamento.getStatus() != Status.AGENDADO) {
            return false;
        }

        agendamento.setStatus(Status.CANCELADO);

        return true;
    }


    
    public boolean editarAgendamento(int id, Cliente cliente,
            Veiculo veiculo, Servico servico, LocalDateTime dataHora) {

        Agendamento agendamento = buscarAgendamento(id);

        if (agendamento == null) {
            return false;
        }

        if (agendamento.getStatus() != Status.AGENDADO) {
            return false;
        }

        if (cliente == null || veiculo == null || servico == null) {
            return false;
        }

        if (!cliente.getVeiculos().contains(veiculo)) {
            return false;
        }

        if (dataHora == null ||
                !verificarHorarioEdicao(id, dataHora)) {

            return false;
        }

        agendamento.setCliente(cliente);
        agendamento.setVeiculo(veiculo);
        agendamento.setServico(servico);
        agendamento.setDataHora(dataHora);

        return true;
    }

  
    
    
    public boolean verificarHorario(LocalDateTime dataHora) {

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getDataHora().equals(dataHora)
                    && agendamento.getStatus() != Status.CANCELADO) {

                return false;
            }
        }

        return true;
    }


    
    
    public boolean verificarHorarioEdicao(int id, LocalDateTime dataHora) {

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getId() != id
                    && agendamento.getDataHora().equals(dataHora)
                    && agendamento.getStatus() != Status.CANCELADO) {

                return false;
            }
        }

        return true;
    }


    
    public boolean iniciarAgendamento(int id) {

        Agendamento agendamento = buscarAgendamento(id);

        if (agendamento == null) {
            return false;
        }

        if (agendamento.getStatus() != Status.AGENDADO) {
            return false;
        }

        agendamento.setStatus(Status.EM_ANDAMENTO);

        return true;
    }


    
    
    public boolean concluirAgendamento(int id) {

        Agendamento agendamento = buscarAgendamento(id);

        if (agendamento == null) {
            return false;
        }

        if (agendamento.getStatus() != Status.EM_ANDAMENTO) {
            return false;
        }

        agendamento.setStatus(Status.CONCLUIDO);

        return true;
    }

  
    
    public ArrayList<Agendamento> getAgendamentos() {

        return agendamentos;
    }
}