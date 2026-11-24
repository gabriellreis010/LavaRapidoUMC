package View;

import View.TelaListarVeiculos;
import View.TelaCliente;
import View.TelaListarServicos;
import View.TelaListarAgendamentos;
import View.TelaGerenciarAgendamentos;
import View.TelaListarClientes;
import View.TelaCadastrarVeiculo;
import View.TelaCadastrarServico;
import View.TelaAgendamento;
import Controller.controleAgendamento;
import Controller.ControleVeiculo;
import Controller.ControleServico;
import Controller.ControleCliente;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private ControleCliente controleCliente = new ControleCliente();
    private ControleVeiculo controleVeiculo = new ControleVeiculo();
    private ControleServico controleServico = new ControleServico();
    private controleAgendamento controleAgendamento = new controleAgendamento();

    public TelaPrincipal() {

        setTitle("UMC wash quickly");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(10, 1, 10, 10));

        painel.setBorder(BorderFactory.createEmptyBorder(
                15, 20, 15, 20));


        
        JLabel titulo = new JLabel("UMC wash quickly");
        titulo.setHorizontalAlignment(JLabel.CENTER);

 
        
        JButton btnCliente = new JButton("Cadastrar Cliente");
        JButton btnListarClientes = new JButton("Listar Clientes");


        
        JButton btnVeiculo = new JButton("Cadastrar Veículo");
        JButton btnListarVeiculos = new JButton("Listar Veículos");


        
        JButton btnServico = new JButton("Cadastrar Serviço");
        JButton btnListarServicos = new JButton("Listar Serviços");

 
        
        JButton btnAgendamento = new JButton("Novo Agendamento");
        JButton btnListar = new JButton("Listar Agendamentos");
        JButton btnGerenciar = new JButton("Gerenciar Agendamentos");

  
        
        painel.add(titulo);

        painel.add(btnCliente);
        painel.add(btnListarClientes);

        painel.add(btnVeiculo);
        painel.add(btnListarVeiculos);

        painel.add(btnServico);
        painel.add(btnListarServicos);

        painel.add(btnAgendamento);
        painel.add(btnListar);
        painel.add(btnGerenciar);

        add(painel);


        
        btnCliente.addActionListener(e -> {

            TelaCliente tela =
                    new TelaCliente(controleCliente);

            tela.setVisible(true);
        });

   
        
        btnListarClientes.addActionListener(e -> {

            TelaListarClientes tela =
                    new TelaListarClientes(
                            controleCliente,
                            controleVeiculo,
                            controleAgendamento);

            tela.setVisible(true);
        });

  
        
        btnVeiculo.addActionListener(e -> {

            TelaCadastrarVeiculo tela =
                    new TelaCadastrarVeiculo(
                            controleCliente,
                            controleVeiculo);

            tela.setVisible(true);
        });

 
        
        btnListarVeiculos.addActionListener(e -> {

            TelaListarVeiculos tela =
                    new TelaListarVeiculos(
                            controleCliente,
                            controleVeiculo,
                            controleAgendamento);

            tela.setVisible(true);
        });


        
        btnServico.addActionListener(e -> {

            TelaCadastrarServico tela =
                    new TelaCadastrarServico(controleServico);

            tela.setVisible(true);
        });


        
        btnListarServicos.addActionListener(e -> {

            TelaListarServicos tela =
                    new TelaListarServicos(controleServico);

            tela.setVisible(true);
        });

 
        
        btnAgendamento.addActionListener(e -> {

            TelaAgendamento tela =
                    new TelaAgendamento(
                            controleCliente,
                            controleServico,
                            controleAgendamento);

            tela.setVisible(true);
        });

  
        
        btnListar.addActionListener(e -> {

            TelaListarAgendamentos tela =
                    new TelaListarAgendamentos(
                            controleAgendamento);

            tela.setVisible(true);
        });


        
        btnGerenciar.addActionListener(e -> {

            TelaGerenciarAgendamentos tela =
                    new TelaGerenciarAgendamentos(
                            controleCliente,
                            controleServico,
                            controleAgendamento);

            tela.setVisible(true);
        });
    }


    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}