import java.net.*;
import java.lang.Thread;
import java.io.IOException;
import java.util.Scanner;

class Servidor_Chat
{
	public static void main(String[] args)
			throws IOException, InterruptedException
	{
		int porta			= 4381;
		ServerSocket servidor_chat	= new ServerSocket(porta);
		Gestor_Conexoes gestao_chat	= new Gestor_Conexoes(servidor_chat);

		servidor_chat.setSoTimeout(5 * 1000);
		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		gestao_chat.start();

		do
		{
			Thread.sleep(3 * 1000);
		}
		while(Conexao.activeCount() != 1);

		System.out.println("______________________________________________");
		System.out.println("Fim do Bate-papo!");

		servidor_chat.close();
	}
}

class Gestor_Conexoes extends Thread
{
	private ServerSocket server;

	public Gestor_Conexoes(ServerSocket server)
	{
		this.server	= server;
	}

	public void run()
	{
		while(Conexao.activeCount() < 3)
		{
			try
			{
				new Conexao(this.server.accept());
			}
			catch (Exception e) {}
		}

		while(Conexao.activeCount() != 2)
		{
			try
			{
					new Conexao(this.server.accept());
			}
			catch (Exception e) {}
		}
	}
}

class Conexao extends Thread
{
	private Socket cliente;

	public Conexao(Socket endereco_cliente)
	{
		this.cliente = endereco_cliente;
		this.start();
	}

	public void run()
	{
		try
		{
			Scanner msg = new Scanner(this.cliente.getInputStream());
			String ip_cliente = this.cliente.getInetAddress().getHostAddress();

			System.out.println(ip_cliente + " se conectou.");

			while(msg.hasNextLine())
				System.out.println(msg.nextLine());

			System.out.println(ip_cliente + " se desconectou.");
			msg.close();
			this.cliente.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
