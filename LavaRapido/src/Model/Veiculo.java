package Model;
public class Veiculo {
    private String carro;
    private String placa;
    
    public Veiculo(String carro, String placa){
        this.carro = carro;
        this.placa = placa;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    
    
}
