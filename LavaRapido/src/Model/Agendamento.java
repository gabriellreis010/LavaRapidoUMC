package Model;
import Model.Veiculo;
import Model.Servico;
import Model.Cliente;
import java.time.LocalDateTime;
public class Agendamento {
    private int id;
    private Cliente cliente;
    private LocalDateTime dataHora;
    private Status status;
    private Servico servico;
    private Veiculo veiculo;
    
    
    public Agendamento (int id, Cliente cliente, LocalDateTime dataHora, Status status, Servico servico, Veiculo veiculo){
        this.id = id;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.status = status;
        this.servico = servico;
        this.veiculo = veiculo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    


    
    
}

