package View;

import Controller.ControleVeiculo;
import Controller.ControleCliente;
import Model.Veiculo;
import Model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaCadastrarVeiculo extends JFrame {

    private ControleCliente controleCliente;
    private ControleVeiculo controleVeiculo;

    private JComboBox<String> comboClientes;
    private JTextField txtCarro;
    private JTextField txtPlaca;

    public TelaCadastrarVeiculo(ControleCliente controleCliente,
                                ControleVeiculo controleVeiculo) {

        this.controleCliente = controleCliente;
        this.controleVeiculo = controleVeiculo;

        setTitle("Cadastro de Veículos");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

       
        
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        painel.setBorder(BorderFactory.createEmptyBorder(
                20, 20, 20, 20));

     
        
        JLabel lblCliente = new JLabel("Cliente:");
        JLabel lblCarro = new JLabel("Carro:");
        JLabel lblPlaca = new JLabel("Placa:");

        comboClientes = new JComboBox<>();

        txtCarro = new JTextField();
        txtPlaca = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

    
        
        painel.add(lblCliente);
        painel.add(comboClientes);

        painel.add(lblCarro);
        painel.add(txtCarro);

        painel.add(lblPlaca);
        painel.add(txtPlaca);

        painel.add(btnCadastrar);
        painel.add(btnVoltar);

        add(painel);

        
        
        carregarClientes();

      
        
        btnCadastrar.addActionListener(e -> cadastrarVeiculo());

      
        
        btnVoltar.addActionListener(e -> dispose());
    }

   
    
    private void carregarClientes() {

        comboClientes.removeAllItems();

        for (Cliente cliente : controleCliente.getClientes()) {

            comboClientes.addItem(
                    cliente.getId() + " - " + cliente.getNome());
        }
    }

    
    
    private void cadastrarVeiculo() {

        if (comboClientes.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                    "Nenhum cliente cadastrado");

            return;
        }

        String carro = txtCarro.getText().trim();
        String placa = txtPlaca.getText().trim().toUpperCase();

       
        if (carro.isEmpty() || placa.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos");

            return;
        }

       
        if (controleVeiculo.buscarVeiculo(placa) != null) {

            JOptionPane.showMessageDialog(this,
                    "Esta placa já está cadastrada");

            return;
        }

     
        
        String clienteSelecionado =
                (String) comboClientes.getSelectedItem();

        int id = Integer.parseInt(
                clienteSelecionado.split(" - ", 2)[0]);

        Cliente cliente = controleCliente.buscarCliente(id);

        if (cliente == null) {

            JOptionPane.showMessageDialog(this,
                    "Cliente não encontrado");

            return;
        }

     
        
        Veiculo veiculo = new Veiculo(carro, placa);

      
        
        boolean cadastrado =
                controleVeiculo.cadastrarVeiculo(veiculo, cliente);

        if (cadastrado) {

            JOptionPane.showMessageDialog(this,
                    "Veículo cadastrado com sucesso");

            txtCarro.setText("");
            txtPlaca.setText("");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Não foi possível cadastrar o veículo");
        }
    }
}