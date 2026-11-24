package View;

import Controller.ControleServico;
import Model.TipoServico;
import Model.Servico;
import javax.swing.*;
import java.awt.*;

public class TelaCadastrarServico extends JFrame {

    private ControleServico controleServico;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JComboBox<TipoServico> comboTipo;
    private JLabel lblPreco;

    public TelaCadastrarServico(ControleServico controleServico) {

        this.controleServico = controleServico;

        setTitle("Cadastrar Serviço");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));

        painel.setBorder(BorderFactory.createEmptyBorder(
                20, 20, 20, 20));

        // Componentes
        txtId = new JTextField();
        txtNome = new JTextField();
        txtDescricao = new JTextField();

        comboTipo = new JComboBox<>(TipoServico.values());

        lblPreco = new JLabel();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

    
        painel.add(new JLabel("ID:"));
        painel.add(txtId);

        painel.add(new JLabel("Nome do serviço:"));
        painel.add(txtNome);

        painel.add(new JLabel("Tipo de serviço:"));
        painel.add(comboTipo);

        painel.add(new JLabel("Descrição:"));
        painel.add(txtDescricao);

        painel.add(new JLabel("Preço:"));
        painel.add(lblPreco);

        painel.add(btnCadastrar);
        painel.add(btnVoltar);

        add(painel);

 
        atualizarPreco();


        comboTipo.addActionListener(e -> atualizarPreco());

        btnCadastrar.addActionListener(e -> cadastrarServico());

        btnVoltar.addActionListener(e -> dispose());
    }


    private void atualizarPreco() {

        TipoServico tipo =
                (TipoServico) comboTipo.getSelectedItem();

        if (tipo != null) {

            Servico servico = new Servico(0, "", tipo, "");

            lblPreco.setText("R$ " +
                    String.format("%.2f", servico.getPreco()));
        }
    }

/*cadastrar serviço*/
    private void cadastrarServico() {

        try {

            int id = Integer.parseInt(txtId.getText().trim());

            String nome = txtNome.getText().trim();

            String descricao = txtDescricao.getText().trim();

            TipoServico tipo =
                    (TipoServico) comboTipo.getSelectedItem();

          
            if (id <= 0) {

                JOptionPane.showMessageDialog(this,
                        "O ID deve ser maior que zero");

                return;
            }

         
            if (nome.isEmpty() || descricao.isEmpty()
                    || tipo == null) {

                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos");

                return;
            }

           
            if (controleServico.buscarServico(id) != null) {

                JOptionPane.showMessageDialog(this,
                        "Já existe um serviço com esse ID");

                return;
            }

        
            Servico servico = new Servico(
                    id, nome, tipo, descricao);

            boolean cadastrado =
                    controleServico.cadastrarServico(servico);

            if (cadastrado) {

                JOptionPane.showMessageDialog(this,
                        "Serviço cadastrado com sucesso");

                limparCampos();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Não foi possível cadastrar o serviço");
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Digite um ID válido");
        }
    }

   
    private void limparCampos() {

        txtId.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        comboTipo.setSelectedIndex(0);

        atualizarPreco();

        txtId.requestFocus();
    }
}