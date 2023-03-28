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
		int max_espera_requisao		= 5;
		ServerSocket servidor_chat	= new ServerSocket(porta);
		Gestor_Conexoes gestao_chat	= new Gestor_Conexoes(servidor_chat);

		servidor_chat.setSoTimeout(max_espera_requisao * 1000);
		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		gestao_chat.start();
		gestao_chat.join();
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
		while(Conexao.con_ativas() == 0)
		{
			try
			{
				new Conexao(this.server.accept());
			}
			catch (Exception e) {}
		}

		while(Conexao.con_ativas() != 0)
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
	private static int ativas = 0;
	private Socket cliente;

	public Conexao(Socket endereco_cliente)
	{
		this.cliente = endereco_cliente;
		this.start();
		Conexao.ativas++;
	}

	public void run()
	{
		try
		{
			Scanner msg		= new Scanner(this.cliente.getInputStream());
			String ip_cliente	= this.cliente.getInetAddress().getHostAddress();

			System.out.println(ip_cliente + " se conectou.");

			while(msg.hasNextLine())
				System.out.println(msg.nextLine());

			System.out.println(ip_cliente + " se desconectou.");
			msg.close();
			this.cliente.close();
			Conexao.ativas--;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static int con_ativas()
	{
		return Conexao.ativas;
	}
}
