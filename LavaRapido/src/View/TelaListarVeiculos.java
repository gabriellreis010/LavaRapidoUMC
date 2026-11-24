package View;

import Controller.controleAgendamento;
import Controller.ControleVeiculo;
import Controller.ControleCliente;
import Model.Veiculo;
import Model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaListarVeiculos extends JFrame {

    private ControleCliente controleCliente;
    private ControleVeiculo controleVeiculo;
    private controleAgendamento controleAgendamento;

    private JTextArea areaVeiculos;

    public TelaListarVeiculos(ControleCliente controleCliente,
            ControleVeiculo controleVeiculo,
            controleAgendamento controleAgendamento) {

        this.controleCliente = controleCliente;
        this.controleVeiculo = controleVeiculo;
        this.controleAgendamento = controleAgendamento;

        setTitle("Lista de Veículos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

     
        
        areaVeiculos = new JTextArea();
        areaVeiculos.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaVeiculos);

        add(scroll, BorderLayout.CENTER);

     
        
        JButton btnExcluir = new JButton("Excluir Veículo");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        
        
        atualizarLista();

     
        
        btnExcluir.addActionListener(e -> excluirVeiculo());

   
        
        btnVoltar.addActionListener(e -> dispose());
    }


    
    public void atualizarLista() {

        String texto = "";

        for (Cliente cliente : controleCliente.getClientes()) {

            for (Veiculo veiculo : cliente.getVeiculos()) {

                texto += "Proprietário: " + cliente.getNome() + "\n";
                texto += "ID do cliente: " + cliente.getId() + "\n";
                texto += "Carro: " + veiculo.getCarro() + "\n";
                texto += "Placa: " + veiculo.getPlaca() + "\n";
            }
        }

        if (texto.isEmpty()) {

            texto = "Nenhum veículo cadastrado";
        }

        areaVeiculos.setText(texto);
    }

  
    
    
    private void excluirVeiculo() {

        String placa = JOptionPane.showInputDialog(
                this, "Digite a placa do veículo:");

        if (placa == null) {
            return;
        }

        placa = placa.trim().toUpperCase();

        
        
        if (placa.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Digite uma placa válida");

            return;
        }

        Veiculo veiculo = controleVeiculo.buscarVeiculo(placa);

      
        
        if (veiculo == null) {

            JOptionPane.showMessageDialog(this,
                    "Veículo não encontrado");

            return;
        }

 
        
        if (controleVeiculo.possuiAgendamentos(
                placa, controleAgendamento)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Não é possível excluir este veículo"
                    + "\nO veículo possui agendamentos vinculados.",
                    "Exclusão bloqueada",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

    
        /*buscar prorpietario do veiculo*/
        Cliente proprietario = null;

        for (Cliente cliente : controleCliente.getClientes()) {

            if (cliente.getVeiculos().contains(veiculo)) {

                proprietario = cliente;
                break;
            }
        }

        if (proprietario == null) {

            JOptionPane.showMessageDialog(this,
                    "Proprietário não encontrado");

            return;
        }

        
        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este veículo?"
                + "\nCarro: " + veiculo.getCarro()
                + "\nPlaca: " + veiculo.getPlaca()
                + "\nProprietário: " + proprietario.getNome(),
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {

            boolean excluido = controleVeiculo.excluirVeiculo(
                    placa,
                    proprietario,
                    controleAgendamento);

            if (excluido) {

                JOptionPane.showMessageDialog(this,
                        "Veículo excluído com sucesso");

                atualizarLista();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Não foi possível excluir o veículo");
            }
        }
    }
}