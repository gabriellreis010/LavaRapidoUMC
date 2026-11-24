package View;

import Controller.controleAgendamento;
import Model.Agendamento;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TelaListarAgendamentos extends JFrame {

    private controleAgendamento controleAgendamento;
    private JTextArea areaAgendamentos;
    private JComboBox<String> comboFiltro;

    private DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaListarAgendamentos(
            controleAgendamento controleAgendamento) {

        this.controleAgendamento = controleAgendamento;

        setTitle("Lista de Agendamentos");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        areaAgendamentos = new JTextArea();
        areaAgendamentos.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaAgendamentos);

        add(scroll, BorderLayout.CENTER);


        comboFiltro = new JComboBox<>(new String[]{
            "Todos os agendamentos",
            "Agenda de hoje",
            "Agenda da semana"
        });

        JPanel painelFiltro = new JPanel();

        painelFiltro.add(new JLabel("Filtrar por:"));
        painelFiltro.add(comboFiltro);

        add(painelFiltro, BorderLayout.NORTH);


        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

      
        atualizarLista();

        // Eventos
        comboFiltro.addActionListener(e -> atualizarLista());

        btnAtualizar.addActionListener(e -> atualizarLista());

        btnVoltar.addActionListener(e -> dispose());
    }

   
    public void atualizarLista() {

        String texto = "";

        LocalDate hoje = LocalDate.now();

        LocalDate inicioSemana = hoje.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate fimSemana = inicioSemana.plusDays(6);

        int filtro = comboFiltro.getSelectedIndex();

        for (Agendamento agendamento :
                controleAgendamento.getAgendamentos()) {

            LocalDate data =
                    agendamento.getDataHora().toLocalDate();

     
            if (filtro == 1 && !data.equals(hoje)) {
                continue;
            }

      
            if (filtro == 2 &&
                    (data.isBefore(inicioSemana)
                    || data.isAfter(fimSemana))) {

                continue;
            }

            texto += "ID: " + agendamento.getId() + "\n";

            texto += "Cliente: "
                    + agendamento.getCliente().getNome() + "\n";

            texto += "Carro: "
                    + agendamento.getVeiculo().getCarro() + "\n";

            texto += "Placa: "
                    + agendamento.getVeiculo().getPlaca() + "\n";

            texto += "Serviço: "
                    + agendamento.getServico().getServico() + "\n";

            texto += "Preço: R$ "
                    + String.format("%.2f",
                            agendamento.getServico().getPreco())
                    + "\n";

            texto += "Data e hora: "
                    + agendamento.getDataHora().format(formato)
                    + "\n";

            texto += "Status: "
                    + agendamento.getStatus() + "\n";

        }

        if (texto.isEmpty()) {

            texto = "Nenhum agendamento encontrado";
        }

        areaAgendamentos.setText(texto);
        areaAgendamentos.setCaretPosition(0);
    }
}