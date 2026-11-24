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

public class TelaAgendamento extends JFrame {

    private ControleCliente controleCliente;
    private ControleServico controleServico;
    private controleAgendamento controleAgendamento;

    private JTextField txtId;
    private JTextField txtData;
    private JTextField txtHora;

    private JComboBox<String> comboClientes;
    private JComboBox<String> comboVeiculos;
    private JComboBox<String> comboServicos;

    private JLabel lblPreco;

    private DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    public TelaAgendamento(
            ControleCliente controleCliente,
            ControleServico controleServico,
            controleAgendamento controleAgendamento) {

        this.controleCliente = controleCliente;
        this.controleServico = controleServico;
        this.controleAgendamento = controleAgendamento;

        setTitle("Novo Agendamento");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(8, 2, 10, 10));

        painel.setBorder(BorderFactory.createEmptyBorder(
                20, 20, 20, 20));

        txtId = new JTextField();
        txtData = new JTextField();
        txtHora = new JTextField();

        comboClientes = new JComboBox<>();
        comboVeiculos = new JComboBox<>();
        comboServicos = new JComboBox<>();

        lblPreco = new JLabel("R$ 0,00");

        JButton btnAgendar = new JButton("Agendar");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(new JLabel("ID:"));
        painel.add(txtId);

        painel.add(new JLabel("Cliente:"));
        painel.add(comboClientes);

        painel.add(new JLabel("Veículo:"));
        painel.add(comboVeiculos);

        painel.add(new JLabel("Serviço:"));
        painel.add(comboServicos);

        painel.add(new JLabel("Preço:"));
        painel.add(lblPreco);

        painel.add(new JLabel("Data (dd/MM/aaaa):"));
        painel.add(txtData);

        painel.add(new JLabel("Hora (HH:mm):"));
        painel.add(txtHora);

        painel.add(btnAgendar);
        painel.add(btnVoltar);

        add(painel);

        carregarClientes();
        carregarVeiculos();
        carregarServicos();
        atualizarPreco();

        // Eventos
        comboClientes.addActionListener(e -> carregarVeiculos());

        comboServicos.addActionListener(e -> atualizarPreco());

        btnAgendar.addActionListener(e -> agendarServico());

        btnVoltar.addActionListener(e -> dispose());
    }


    
    private void carregarClientes() {

        comboClientes.removeAllItems();

        for (Cliente cliente : controleCliente.getClientes()) {

            comboClientes.addItem(
                    cliente.getId() + " - " + cliente.getNome());
        }
    }

  
    
    private Cliente buscarClienteSelecionado() {

        String selecionado =
                (String) comboClientes.getSelectedItem();

        if (selecionado == null) {
            return null;
        }

        int id = Integer.parseInt(
                selecionado.split(" - ", 2)[0]);

        return controleCliente.buscarCliente(id);
    }

  
    
    
    private void carregarVeiculos() {

        comboVeiculos.removeAllItems();

        Cliente cliente = buscarClienteSelecionado();

        if (cliente == null) {
            return;
        }

        for (Veiculo veiculo : cliente.getVeiculos()) {

            comboVeiculos.addItem(
                    veiculo.getPlaca() + " - "
                    + veiculo.getCarro());
        }
    }


    
    private void carregarServicos() {

        comboServicos.removeAllItems();

        for (Servico servico : controleServico.getServicos()) {

            comboServicos.addItem(
                    servico.getId() + " - " + servico.getNome());
        }
    }


    
    private Servico buscarServicoSelecionado() {

        String selecionado =
                (String) comboServicos.getSelectedItem();

        if (selecionado == null) {
            return null;
        }

        int id = Integer.parseInt(
                selecionado.split(" - ", 2)[0]);

        return controleServico.buscarServico(id);
    }

 
    
    private void atualizarPreco() {

        Servico servico = buscarServicoSelecionado();

        if (servico != null) {

            lblPreco.setText("R$ " +
                    String.format("%.2f", servico.getPreco()));

        } else {

            lblPreco.setText("R$ 0,00");
        }
    }

/*realizar agendamento */
    private void agendarServico() {

        try {

            int id = Integer.parseInt(txtId.getText().trim());

            if (id <= 0) {

                JOptionPane.showMessageDialog(this,
                        "O ID deve ser maior que zero");

                return;
            }

            if (controleAgendamento.buscarAgendamento(id) != null) {

                JOptionPane.showMessageDialog(this,
                        "Já existe um agendamento com esse ID");

                return;
            }

            Cliente cliente = buscarClienteSelecionado();

            if (cliente == null) {

                JOptionPane.showMessageDialog(this,
                        "Selecione um cliente");

                return;
            }

            String veiculoSelecionado =
                    (String) comboVeiculos.getSelectedItem();

            if (veiculoSelecionado == null) {

                JOptionPane.showMessageDialog(this,
                        "O cliente precisa possuir um veículo");

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

            Servico servico = buscarServicoSelecionado();

            if (servico == null) {

                JOptionPane.showMessageDialog(this,
                        "Cadastre e selecione um serviço");

                return;
            }

            LocalDateTime dataHora = LocalDateTime.parse(
                    txtData.getText().trim() + " "
                    + txtHora.getText().trim(), formato);

            if (dataHora.isBefore(LocalDateTime.now())) {

                JOptionPane.showMessageDialog(this,
                        "Não é possível agendar uma data passada");

                return;
            }

            if (!controleAgendamento.verificarHorario(dataHora)) {

                JOptionPane.showMessageDialog(this,
                        "Horário indisponível");

                return;
            }

            Agendamento agendamento = new Agendamento(
                    id,
                    cliente,
                    dataHora,
                    Status.AGENDADO,
                    servico,
                    veiculo);

            boolean agendado =
                    controleAgendamento.agendarServico(agendamento);

            if (agendado) {

                JOptionPane.showMessageDialog(this,
                        "Agendamento realizado com sucesso");

                txtId.setText("");
                txtData.setText("");
                txtHora.setText("");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Não foi possível realizar o agendamento");
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Digite um ID válido");

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(this,
                    "Data ou hora inválida");
        }
    }
}