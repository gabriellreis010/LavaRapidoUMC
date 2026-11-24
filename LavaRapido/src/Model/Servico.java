package Model;


public class Servico {
    private int id;
    private String nome;
    private TipoServico servico;
    private String descricao;
    private double preco;
    
    public Servico(int id, String nome, TipoServico servico,
               String descricao) {

    this.id = id;
    this.nome = nome;
    this.servico = servico;
    this.descricao = descricao;
    this.definirPreco();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

 
    public TipoServico getServico() {
        return servico;
    }

    public void setServico(TipoServico servico) {
    this.servico = servico;
    this.definirPreco();
    }
    
    
    public void definirPreco(){
        if (servico == TipoServico.LAVAGEM_SIMPLES){
            preco = 20.00;
        }
        else if (servico == TipoServico.LAVAGEM_COMPLETA){
            preco = 40.00;
        }
        else if (servico == TipoServico.LAVAGEM_DETALHADA){
            preco = 60.00;
        }
        else if (servico == TipoServico.PRETINHO){
            preco = 10.00;
        }
    
            
    }
    

    
   
    
    
}
