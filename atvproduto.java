
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class atvproduto {
    public static void main(String[] args) {
        

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Notebook", 5500, "Eletrônicos"));
        produtos.add(new Produto("Mouse", 80, "Eletrônicos"));
        produtos.add(new Produto("A Revolução dos Bichos", 30, "Livros"));
        produtos.add(new Produto("Celular", 1800, "Eletrônicos"));
        produtos.add(new Produto("1984", 45, "Livros"));
        produtos.add(new Produto("Kindle", 700, "Eletrônicos"));
        produtos.add(new Produto("O Senhor dos Anéis", 85, "Livros"));
        produtos.add(new Produto("Fone de Ouvido", 250, "Eletrônicos"));
        
        System.out.println("Produtos Criados");
        produtos.forEach(p -> System.out.println(p));

        System.out.println("\na. Produtos Eletrônicos");
        for (Produto p : produtos) {
            if (p.getCategoria().equals("Eletrônicos")) {
                System.out.println(p.getNome());
            }
        }

        System.out.println("\na. Produtos Eletrônicos (Stream API)");
        produtos.stream()
                .filter(p -> p.getCategoria().equals("Eletrônicos"))
                .forEach(p -> System.out.println(p.getNome()));

        System.out.println("\nb. Preços de produtos acima de 500");
        List<Double> precosAcimaDe500 = produtos.stream()
                .filter(p -> p.getPreco() > 500)
                .map(p -> p.getPreco())
                .collect(Collectors.toList());
        
        precosAcimaDe500.forEach(System.out::println);

        System.out.println("\nc. Valor total do estoque de Livros");
        double totalLivros = produtos.stream()
                .filter(p -> p.getCategoria().equals("Livros"))
                .mapToDouble(p -> p.getPreco())
                .sum();
        
        System.out.printf("O valor total dos livros é: %.2f%n", totalLivros);

        System.out.println("\nd./e. Busca de produto por nome");
        

        Optional<Produto> produtoExistente = buscarProdutoPorNome(produtos, "Celular");
        System.out.println("Buscando 'Celular'");
        produtoExistente.ifPresent(p -> System.out.println("Encontrado: " + p));

  
        System.out.println("\nBuscando 'Cadeira'");
        try {
            Produto produtoNaoExistente = buscarProdutoPorNome(produtos, "Cadeira")
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        } catch (RuntimeException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        System.out.println("\nf. Mapeando para nomes de produtos");
        

        List<String> nomesLambda = produtos.stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());
        System.out.println("Com Lambda: " + nomesLambda);
        
        List<String> nomesRefMethod = produtos.stream()
                .map(Produto::getNome)
                .collect(Collectors.toList());
        System.out.println("Com Method Reference: " + nomesRefMethod);
    }


    public static Optional<Produto> buscarProdutoPorNome(List<Produto> produtos, String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    } 
}
