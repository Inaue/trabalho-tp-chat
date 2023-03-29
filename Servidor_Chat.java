import java.net.*;
import java.lang.Thread;
import java.io.IOException;
import java.util.Scanner;

class Servidor_Chat
{
	public static void main(String[] args)
			throws IOException, InterruptedException
	{
		int porta_server	= 1234;
		int max_t_req		= 5;
		ServerSocket servidor	= new ServerSocket(porta_server);
		Adm_Conexoes i_input	= new Adm_Conexoes(servidor);

		servidor.setSoTimeout(max_t_req * 1000);
		System.out.println("App de Conversacoes");
		System.out.println("______________________________________________");
		i_input.start();
		i_input.join();
		servidor.close();
		System.out.println("______________________________________________");
		System.out.println("Fim do Bate-papo!");
	}
}

class Adm_Conexoes extends Thread
{
	private ServerSocket server;

	public Adm_Conexoes(ServerSocket server)
	{
		this.server	= server;
	}

	public void run()
	{
		while(Receptor.con_ativas() == 0)
		{
			try
			{
				new Receptor(this.server.accept());
			}
			catch (Exception e) {}
		}

		while(Receptor.con_ativas() != 0)
		{
			try
			{
				new Receptor(this.server.accept());
			}
			catch (Exception e) {}
		}
	}
}

class Receptor extends Thread
{
	private static int ativas = 0;
	private Socket cliente;

	public Receptor(Socket endereco_cliente)
	{
		this.cliente = endereco_cliente;
		this.start();
		Receptor.ativas++;
	}

	public void run()
	{
		try
		{
			Scanner msg	= new Scanner(this.cliente.getInputStream());

			while(msg.hasNextLine())
				System.out.println(msg.nextLine());

			msg.close();
			this.cliente.close();
			Receptor.ativas--;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static int con_ativas()
	{
		return Receptor.ativas;
	}
}

