public class App{
    public static void main(String[] args) {
        String nome = "Thiago";
        String telefone = "213142";
        String enderecoEntrega = "123131";
        String complemento = "1321321";

        Cliente cliente1 = new Cliente(nome, telefone, enderecoEntrega, complemento);

        System.out.println(cliente1.toString());
    }
}