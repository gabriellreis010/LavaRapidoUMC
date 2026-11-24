package Controller;
import Model.Agendamento;
import Model.Veiculo;
import Model.Cliente;
import java.util.ArrayList;

public class ControleCliente {

    private ArrayList<Cliente> clientes = new ArrayList<>();

 
    
    public void cadastrarCliente(Cliente cliente) {

        clientes.add(cliente);
    }

  
    
    
    public void listarClientes() {

        if (clientes.isEmpty()) {

            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        for (Cliente cliente : clientes) {

            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());

            if (cliente.getVeiculos().isEmpty()) {

                System.out.println("Cliente não possui nenhum veículo");
            }

            for (Veiculo veiculo : cliente.getVeiculos()) {

                System.out.println("Carro: " + veiculo.getCarro());
                System.out.println("Placa: " + veiculo.getPlaca());
            }
        }
    }
    
    

  /*metodo para buscar cliente */
    public Cliente buscarCliente(int id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId() == id) {

                return cliente;
            }
        }

        return null;
    }

   
    
    public boolean editarCliente(int id, String nome, String cpf,
            String telefone, String email) {

        Cliente cliente = buscarCliente(id);

        if (cliente == null) {
            return false;
        }

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        return true;
    }

   
    
    public boolean possuiAgendamentos(int id,
            controleAgendamento controleAgendamento) {

        for (Agendamento agendamento :
                controleAgendamento.getAgendamentos()) {

            if (agendamento.getCliente().getId() == id) {

                return true;
            }
        }

        return false;
    }

   
    
    public boolean excluirCliente(int id,
            ControleVeiculo controleVeiculo,
            controleAgendamento controleAgendamento) {

        Cliente cliente = buscarCliente(id);

      
        
        if (cliente == null) {
            return false;
        }

        
        
        if (possuiAgendamentos(id, controleAgendamento)) {

            return false;
        }

        
        
        
        ArrayList<Veiculo> veiculosCliente =
                new ArrayList<>(cliente.getVeiculos());

       
       
        
        for (Veiculo veiculo : veiculosCliente) {

            if (controleVeiculo.buscarVeiculo(
                    veiculo.getPlaca()) != veiculo) {

                return false;
            }

            if (controleVeiculo.possuiAgendamentos(
                    veiculo.getPlaca(), controleAgendamento)) {

                return false;
            }
        }

       
        
        for (Veiculo veiculo : veiculosCliente) {

            if (!controleVeiculo.excluirVeiculo(
                    veiculo.getPlaca(),
                    cliente,
                    controleAgendamento)) {

                return false;
            }
        }

        // Excluir cliente
        clientes.remove(cliente);

        return true;
    }

   
    
    public boolean adicionarVeiculoCliente(int id, Veiculo veiculo) {

        Cliente cliente = buscarCliente(id);

        if (cliente == null || veiculo == null) {
            return false;
        }

        
        for (Cliente c : clientes) {

            for (Veiculo v : c.getVeiculos()) {

                if (v.getPlaca().equalsIgnoreCase(
                        veiculo.getPlaca())) {

                    return false;
                }
            }
        }

        cliente.adicionarVeiculo(veiculo);

        return true;
    }

  
    public ArrayList<Cliente> getClientes() {

        return clientes;
    }
}