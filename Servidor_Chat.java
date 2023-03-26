import java.net.*;
import java.io.IOException;
import java.util.Scanner;

class Servidor_Chat
{
	public static void main(String[] args) throws IOException
	{
		int porta		= 4381;
		ServerSocket servidor	= new ServerSocket(porta);
		Socket cliente		= servidor.accept();
        	Scanner msg		= new Scanner(cliente.getInputStream());

		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		System.out.println("Conectado com usuario " + cliente.getInetAddress().getHostAddress());

		while(msg.hasNextLine())
			System.out.println(msg.nextLine());

		msg.close();
		cliente.close();
		servidor.close();
	}
}
