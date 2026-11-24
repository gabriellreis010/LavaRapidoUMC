package View;

import Controller.controleAgendamento;
import Controller.ControleServico;
import Controller.ControleCliente;
import Model.Status;
import Model.Agendamento;
import Model.Veiculo;
import Model.Servico;
import Model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class TelaGerenciarAgendamentos extends JFrame {

    private ControleCliente controleCliente;
    private ControleServico controleServico;
    private controleAgendamento controleAgendamento;

    private JTextField txtId;

    private DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    public TelaGerenciarAgendamentos(
            ControleCliente controleCliente,
            ControleServico controleServico,
            controleAgendamento controleAgendamento) {

        this.controleCliente = controleCliente;
        this.controleServico = controleServico;
        this.controleAgendamento = controleAgendamento;

        setTitle("Gerenciar Agendamentos");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(8, 1, 10, 10));

        painel.setBorder(BorderFactory.createEmptyBorder(
                20, 20, 20, 20));

        JLabel titulo = new JLabel("Gerenciar Agendamentos");
        titulo.setHorizontalAlignment(JLabel.CENTER);

        txtId = new JTextField();

        JButton btnEditar = new JButton("Editar Agendamento");
        JButton btnCancelar = new JButton("Cancelar Agendamento");
        JButton btnIniciar = new JButton("Iniciar Serviço");
        JButton btnConcluir = new JButton("Concluir Serviço");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(titulo);
        painel.add(new JLabel("ID do agendamento:"));
        painel.add(txtId);
        painel.add(btnEditar);
        painel.add(btnCancelar);
        painel.add(btnIniciar);
        painel.add(btnConcluir);
        painel.add(btnVoltar);

        add(painel);


        
        btnEditar.addActionListener(e -> editarAgendamento());

        btnCancelar.addActionListener(e -> cancelarAgendamento());

        btnIniciar.addActionListener(e -> iniciarAgendamento());

        btnConcluir.addActionListener(e -> concluirAgendamento());

        btnVoltar.addActionListener(e -> dispose());
    }


    
    private Agendamento buscarAgendamento() {

        try {

            int id = Integer.parseInt(txtId.getText().trim());

            Agendamento agendamento =
                    controleAgendamento.buscarAgendamento(id);

            if (agendamento == null) {

                JOptionPane.showMessageDialog(this,
                        "Agendamento não encontrado");
            }

            return agendamento;

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Digite um ID válido");

            return null;
        }
    }


    
    private void cancelarAgendamento() {

        Agendamento agendamento = buscarAgendamento();

        if (agendamento == null) {
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente cancelar esse agendamento?",
                "Confirmar cancelamento",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        boolean cancelado =
                controleAgendamento.cancelarAgendamento(
                        agendamento.getId());

        if (cancelado) {

            JOptionPane.showMessageDialog(this,
                    "Agendamento cancelado com sucesso");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Não é possível cancelar um agendamento com status "
                    + agendamento.getStatus());
        }
    }


    
    private void iniciarAgendamento() {

        Agendamento agendamento = buscarAgendamento();

        if (agendamento == null) {
            return;
        }

        boolean iniciado =
                controleAgendamento.iniciarAgendamento(
                        agendamento.getId());

        if (iniciado) {

            JOptionPane.showMessageDialog(this,
                    "Serviço iniciado com sucesso!");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Não é possível iniciar um serviço com status "
                    + agendamento.getStatus());
        }
    }


    
    private void concluirAgendamento() {

        Agendamento agendamento = buscarAgendamento();

        if (agendamento == null) {
            return;
        }

        boolean concluido =
                controleAgendamento.concluirAgendamento(
                        agendamento.getId());

        if (concluido) {

            JOptionPane.showMessageDialog(this,
                    "Serviço concluído com sucesso");

        } else {

            JOptionPane.showMessageDialog(this,
                    "Não é possível concluir um serviço com status "
                    + agendamento.getStatus());
        }
    }


    
    private void editarAgendamento() {

        Agendamento agendamento = buscarAgendamento();

        if (agendamento == null) {
            return;
        }

        if (agendamento.getStatus() != Status.AGENDADO) {

            JOptionPane.showMessageDialog(this,
                    "Somente agendamentos com status AGENDADO "
                    + "podem ser editados");

            return;
        }

        JComboBox<String> comboClientes = new JComboBox<>();
        JComboBox<String> comboVeiculos = new JComboBox<>();
        JComboBox<String> comboServicos = new JComboBox<>();

        JLabel lblPreco = new JLabel("R$ 0,00");


        
        for (Cliente cliente : controleCliente.getClientes()) {

            comboClientes.addItem(
                    cliente.getId() + " - " + cliente.getNome());
        }

  
        
        comboClientes.setSelectedItem(
                agendamento.getCliente().getId() + " - "
                + agendamento.getCliente().getNome());

      
        
        Runnable carregarVeiculos = () -> {

            comboVeiculos.removeAllItems();

            String selecionado =
                    (String) comboClientes.getSelectedItem();

            if (selecionado == null) {
                return;
            }

            int id = Integer.parseInt(
                    selecionado.split(" - ", 2)[0]);

            Cliente cliente = controleCliente.buscarCliente(id);

            if (cliente != null) {

                for (Veiculo veiculo : cliente.getVeiculos()) {

                    comboVeiculos.addItem(
                            veiculo.getPlaca() + " - "
                            + veiculo.getCarro());
                }
            }
        };

        carregarVeiculos.run();

        comboClientes.addActionListener(
                e -> carregarVeiculos.run());

    
        
        comboVeiculos.setSelectedItem(
                agendamento.getVeiculo().getPlaca() + " - "
                + agendamento.getVeiculo().getCarro());

      
        
        for (Servico servico : controleServico.getServicos()) {

            comboServicos.addItem(
                    servico.getId() + " - " + servico.getNome());
        }

      
        
        comboServicos.setSelectedItem(
                agendamento.getServico().getId() + " - "
                + agendamento.getServico().getNome());

    
        
        Runnable atualizarPreco = () -> {

            String selecionado =
                    (String) comboServicos.getSelectedItem();

            if (selecionado == null) {

                lblPreco.setText("R$ 0,00");
                return;
            }

            int id = Integer.parseInt(
                    selecionado.split(" - ", 2)[0]);

            Servico servico = controleServico.buscarServico(id);

            if (servico != null) {

                lblPreco.setText("R$ " +
                        String.format("%.2f", servico.getPreco()));

            } else {

                lblPreco.setText("R$ 0,00");
            }
        };

        atualizarPreco.run();

        comboServicos.addActionListener(
                e -> atualizarPreco.run());

        JTextField txtDataHora = new JTextField(
                agendamento.getDataHora().format(formato));

        // Painel de edição
        JPanel painelEdicao = new JPanel(
                new GridLayout(5, 2, 10, 10));

        painelEdicao.add(new JLabel("Cliente:"));
        painelEdicao.add(comboClientes);

        painelEdicao.add(new JLabel("Veículo:"));
        painelEdicao.add(comboVeiculos);

        painelEdicao.add(new JLabel("Serviço:"));
        painelEdicao.add(comboServicos);

        painelEdicao.add(new JLabel("Preço:"));
        painelEdicao.add(lblPreco);

        painelEdicao.add(new JLabel("Data e hora:"));
        painelEdicao.add(txtDataHora);

        int resposta = JOptionPane.showConfirmDialog(
                this,
                painelEdicao,
                "Editar Agendamento",
                JOptionPane.OK_CANCEL_OPTION);

        if (resposta != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            String clienteSelecionado =
                    (String) comboClientes.getSelectedItem();

            String veiculoSelecionado =
                    (String) comboVeiculos.getSelectedItem();

            String servicoSelecionado =
                    (String) comboServicos.getSelectedItem();

            if (clienteSelecionado == null ||
                    veiculoSelecionado == null ||
                    servicoSelecionado == null) {

                JOptionPane.showMessageDialog(this,
                        "Selecione um cliente, veículo e serviço");

                return;
            }

           
            
            int idCliente = Integer.parseInt(
                    clienteSelecionado.split(" - ", 2)[0]);

            Cliente cliente =
                    controleCliente.buscarCliente(idCliente);

            if (cliente == null) {

                JOptionPane.showMessageDialog(this,
                        "Cliente não encontrado");

                return;
            }

         
            String placa =
                    veiculoSelecionado.split(" - ", 2)[0];

            Veiculo veiculo = null;

            for (Veiculo v : cliente.getVeiculos()) {

                if (v.getPlaca().equalsIgnoreCase(placa)) {

                    veiculo = v;
                    break;
                }
            }

            if (veiculo == null) {

                JOptionPane.showMessageDialog(this,
                        "Veículo não encontrado");

                return;
            }

           
            int idServico = Integer.parseInt(
                    servicoSelecionado.split(" - ", 2)[0]);

            Servico servico =
                    controleServico.buscarServico(idServico);

            if (servico == null) {

                JOptionPane.showMessageDialog(this,
                        "Serviço não encontrado");

                return;
            }

         
            LocalDateTime dataHora = LocalDateTime.parse(
                    txtDataHora.getText().trim(), formato);

            if (dataHora.isBefore(LocalDateTime.now())) {

                JOptionPane.showMessageDialog(this,
                        "Não é possível utilizar uma data passada");

                return;
            }

            if (!controleAgendamento.verificarHorarioEdicao(
                    agendamento.getId(), dataHora)) {

                JOptionPane.showMessageDialog(this,
                        "Horário indisponível");

                return;
            }

        
            boolean editado =
                    controleAgendamento.editarAgendamento(
                            agendamento.getId(),
                            cliente,
                            veiculo,
                            servico,
                            dataHora);

            if (editado) {

                JOptionPane.showMessageDialog(this,
                        "Agendamento editado com sucesso");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Não foi possível editar o agendamento");
            }

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(this,
                    "Data ou hora inválida Utilize dd/MM/aaaa HH:mm");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Dados inválidos");
        }
    }
}