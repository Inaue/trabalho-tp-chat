import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.net.*;

public class Servidor_Chat
{
	public static void main(String[] args) throws IOException
	{
		Collection<PrintStream> saidas	= new HashSet<PrintStream>(); 
		ServerSocket servidor		= new ServerSocket(1234);
		int max_t_req			= 5;
		Scanner entrada;
		PrintStream saida;
		Socket cliente;

		while (true)
		{
			cliente = servidor.accept();
			entrada	= new Scanner(cliente.getInputStream());
			saida	= new PrintStream(cliente.getOutputStream());
			saidas.add(saida);
			new Megafone(entrada, saidas).start();
		}
	}
}

class Megafone extends Thread
{
	private Scanner in;
	private Collection<PrintStream> out;

	public Megafone(Scanner in, Collection<PrintStream> out)
		throws IOException
	{
		this.in		= in;
		this.out	= out;
	}

	public void run()
	{
		String msg;

		while (this.in.hasNextLine())
		{
			msg	= this.in.nextLine();

			for(PrintStream cliente : this.out)
				cliente.println(msg);
		}

		this.in.close();
	}
}
