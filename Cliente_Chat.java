import java.net.*;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.IOException;

class Cliente_Chat
{
	public static void main(String[] args)
			throws IOException, InterruptedException
	{
		String ip		= new String("127.0.0.1");
		int porta_server	= 1234;
		Emissor i_output	= new Emissor(ip, porta_server);

		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		i_output.start();
		i_output.join();
		System.out.println("______________________________________________");
		System.out.println("Fim do Bate-papo!");
	}
}

class Emissor extends Thread
{
	private Socket receptor;
	
	public Emissor(String ip, int porta)
	{
		try
		{
			this.receptor	= new Socket(ip, porta);
		}
		catch(Exception e) {}
	}

	public void run()
	{
		try
		{
			Scanner teclado		= new Scanner(System.in);
			PrintStream saida	= new PrintStream(this.receptor.getOutputStream());

			while(teclado.hasNextLine())
				saida.println(teclado.nextLine());

			teclado.close();
			saida.close();
			this.receptor.close();
		}
		catch(Exception e) {}
	}
}

