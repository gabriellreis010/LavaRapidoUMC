package View;

import Controller.ControleCliente;
import Model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaCliente extends JFrame {

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoTelefone;
    private JTextField campoEmail;

    private JButton btnCadastrar;

    private ControleCliente controleCliente;

    public TelaCliente(ControleCliente controleCliente) {

        this.controleCliente = controleCliente;

        setTitle("Cadastro de Clientes");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 2, 10, 10));

        campoId = new JTextField();
        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoTelefone = new JTextField();
        campoEmail = new JTextField();

        btnCadastrar = new JButton("Cadastrar");

        painel.add(new JLabel("ID:"));
        painel.add(campoId);

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        painel.add(new JLabel("CPF:"));
        painel.add(campoCpf);

        painel.add(new JLabel("Telefone:"));
        painel.add(campoTelefone);

        painel.add(new JLabel("Email:"));
        painel.add(campoEmail);

        painel.add(new JLabel(""));
        painel.add(btnCadastrar);

        add(painel);
        btnCadastrar.addActionListener(e -> {

    try {

        int id = Integer.parseInt(campoId.getText());
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String telefone = campoTelefone.getText();
        String email = campoEmail.getText();

        if (nome.trim().isEmpty() || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha o nome e o CPF");
            return;
        }

        if (controleCliente.buscarCliente(id) != null) {
            JOptionPane.showMessageDialog(this,
                    "Já existe um cliente com esse ID");
            return;
        }

        Cliente cliente = new Cliente(id, nome, cpf, telefone, email);

        controleCliente.cadastrarCliente(cliente);

        JOptionPane.showMessageDialog(this,
                "Cliente cadastrado com sucesso");

        campoId.setText("");
        campoNome.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(this,
                "Digite um ID válido");
    }

    });
    }
 }