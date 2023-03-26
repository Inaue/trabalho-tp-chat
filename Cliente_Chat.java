import java.net.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

class Cliente_Chat
{
	public static void main(String[] args)
			throws UnknownHostException , IOException
	{
		String ip		= new String("127.0.0.1");
		int porta		= 4381;
		Socket cliente		= new Socket(ip, porta);
        	Scanner teclado		= new Scanner(System.in);
		PrintStream saida	= new PrintStream(cliente.getOutputStream());

		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		System.out.println("Conectado com usuario " + cliente.getInetAddress().getHostAddress());

		while(teclado.hasNextLine())
			saida.println(teclado.nextLine());

		teclado.close();
		saida.close();
		cliente.close();
	}
}
