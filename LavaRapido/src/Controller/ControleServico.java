package Controller;

import Model.Servico;
import java.util.ArrayList;

public class ControleServico {

    private ArrayList<Servico> servicos = new ArrayList<>();

   
    
    public boolean cadastrarServico(Servico servico) {

        if (servico == null) {
            return false;
        }

        if (servico.getId() <= 0 ||
                servico.getNome() == null ||
                servico.getNome().trim().isEmpty() ||
                servico.getServico() == null ||
                servico.getDescricao() == null ||
                servico.getDescricao().trim().isEmpty()) {

            return false;
        }

        if (buscarServico(servico.getId()) != null) {
            return false;
        }

        servicos.add(servico);

        return true;
    }

    
  
    public Servico buscarServico(int id) {

        for (Servico servico : servicos) {

            if (servico.getId() == id) {
                return servico;
            }
        }

        return null;
    }

 
    
    public ArrayList<Servico> getServicos() {

        return servicos;
    }
}