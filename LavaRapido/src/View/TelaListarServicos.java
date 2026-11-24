package View;

import Controller.ControleServico;
import Model.Servico;
import javax.swing.*;
import java.awt.*;

public class TelaListarServicos extends JFrame {

    private ControleServico controleServico;
    private JTextArea areaServicos;

    public TelaListarServicos(ControleServico controleServico) {

        this.controleServico = controleServico;

        setTitle("Lista de Serviços");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    
        JLabel titulo = new JLabel("Serviços Cadastrados");
        titulo.setHorizontalAlignment(JLabel.CENTER);

        add(titulo, BorderLayout.NORTH);

    /*listagem */
        areaServicos = new JTextArea();
        areaServicos.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaServicos);

        add(scroll, BorderLayout.CENTER);

  
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelBotoes = new JPanel();

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

      
        atualizarLista();


        btnAtualizar.addActionListener(e -> atualizarLista());

        btnVoltar.addActionListener(e -> dispose());
    }

  
    public void atualizarLista() {

        String texto = "";

        for (Servico servico : controleServico.getServicos()) {

            texto += "ID: " + servico.getId() + "\n";

            texto += "Nome: " + servico.getNome() + "\n";

            texto += "Tipo: " + servico.getServico() + "\n";

            texto += "Descrição: "
                    + servico.getDescricao() + "\n";

            texto += "Preço: R$ "
                    + String.format("%.2f", servico.getPreco())
                    + "\n";

        }

        if (texto.isEmpty()) {

            texto = "Nenhum serviço cadastrado";
        }

        areaServicos.setText(texto);
        areaServicos.setCaretPosition(0);
    }
}