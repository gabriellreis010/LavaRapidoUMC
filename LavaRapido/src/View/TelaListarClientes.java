package View;

import Controller.controleAgendamento;
import Controller.ControleVeiculo;
import Controller.ControleCliente;
import Model.Veiculo;
import Model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaListarClientes extends JFrame {

    private ControleCliente controleCliente;
    private ControleVeiculo controleVeiculo;
    private controleAgendamento controleAgendamento;

    private JTextArea areaClientes;

    public TelaListarClientes(ControleCliente controleCliente,
            ControleVeiculo controleVeiculo,
            controleAgendamento controleAgendamento) {

        this.controleCliente = controleCliente;
        this.controleVeiculo = controleVeiculo;
        this.controleAgendamento = controleAgendamento;

        setTitle("Lista de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    
        
        areaClientes = new JTextArea();
        areaClientes.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaClientes);

        add(scroll, BorderLayout.CENTER);


        
        JButton btnEditar = new JButton("Editar Cliente");
        JButton btnExcluir = new JButton("Excluir Cliente");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);

     
        atualizarLista();

     
        
        btnEditar.addActionListener(e -> {

            try {

                String textoId = JOptionPane.showInputDialog(
                        this, "Digite o ID do cliente:");

                if (textoId == null) {
                    return;
                }

                int id = Integer.parseInt(textoId.trim());

                Cliente cliente = controleCliente.buscarCliente(id);

                if (cliente == null) {

                    JOptionPane.showMessageDialog(this,
                            "Cliente não encontrado");
                    return;
                }

                String nome = JOptionPane.showInputDialog(
                        this, "Nome:", cliente.getNome());

                if (nome == null) {
                    return;
                }

                String cpf = JOptionPane.showInputDialog(
                        this, "CPF:", cliente.getCpf());

                if (cpf == null) {
                    return;
                }

                String telefone = JOptionPane.showInputDialog(
                        this, "Telefone:", cliente.getTelefone());

                if (telefone == null) {
                    return;
                }

                String email = JOptionPane.showInputDialog(
                        this, "Email:", cliente.getEmail());

                if (email == null) {
                    return;
                }

        
                if (nome.trim().isEmpty() ||
                        cpf.trim().isEmpty() ||
                        telefone.trim().isEmpty() ||
                        email.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Preencha todos os campos");

                    return;
                }

                boolean editado = controleCliente.editarCliente(
                        id, nome, cpf, telefone, email);

                if (editado) {

                    JOptionPane.showMessageDialog(this,
                            "Cliente editado com sucesso");

                    atualizarLista();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Não foi possível editar o cliente");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Digite um ID válido");
            }
        });

      /*botão excluir cliente*/
        btnExcluir.addActionListener(e -> {

            try {

                String textoId = JOptionPane.showInputDialog(
                        this, "Digite o ID do cliente:");

                if (textoId == null) {
                    return;
                }

                int id = Integer.parseInt(textoId.trim());

                Cliente cliente = controleCliente.buscarCliente(id);

                // Verificar se o cliente existe
                if (cliente == null) {

                    JOptionPane.showMessageDialog(this,
                            "Cliente não encontrado");

                    return;
                }

             
                
                if (controleCliente.possuiAgendamentos(
                        id, controleAgendamento)) {

                    JOptionPane.showMessageDialog(this,
                            "Não é possível excluir este cliente!"
                            + "\nO cliente possui agendamentos vinculados.",
                            "Exclusão bloqueada",
                            JOptionPane.WARNING_MESSAGE);

                    return;
                }

         
                
                String mensagem = "Deseja realmente excluir "
                        + cliente.getNome() + "?";

           
                
                if (!cliente.getVeiculos().isEmpty()) {

                    mensagem += "\n\nTodos os veículos deste cliente"
                            + " também serão excluídos.";
                }

                int confirmacao = JOptionPane.showConfirmDialog(
                        this,
                        mensagem,
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirmacao == JOptionPane.YES_OPTION) {

                    boolean excluido =
                            controleCliente.excluirCliente(
                                    id,
                                    controleVeiculo,
                                    controleAgendamento);

                    if (excluido) {

                        JOptionPane.showMessageDialog(this,
                                "Cliente excluído com sucesso");

                        atualizarLista();

                    } else {

                        JOptionPane.showMessageDialog(this,
                                "Não foi possível excluir o cliente");

                        atualizarLista();
                    }
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Digite um ID válido");
            }
        });
    }


    
    public void atualizarLista() {

        String texto = "";

        for (Cliente cliente : controleCliente.getClientes()) {

            texto += "ID: " + cliente.getId() + "\n";
            texto += "Nome: " + cliente.getNome() + "\n";
            texto += "CPF: " + cliente.getCpf() + "\n";
            texto += "Telefone: " + cliente.getTelefone() + "\n";
            texto += "Email: " + cliente.getEmail() + "\n";

            if (cliente.getVeiculos().isEmpty()) {

                texto += "Cliente não possui veículos.\n";

            } else {

                for (Veiculo veiculo : cliente.getVeiculos()) {

                    texto += "Carro: " + veiculo.getCarro() + "\n";
                    texto += "Placa: " + veiculo.getPlaca() + "\n";
                }
            }

        }

        if (texto.isEmpty()) {

            texto = "Nenhum cliente cadastrado";
        }

        areaClientes.setText(texto);
    }
}