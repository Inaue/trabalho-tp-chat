import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.net.*;

class Cliente_Chat
{
    public static void main(String[] args)
        throws IOException, UnknownHostException, InterruptedException
    {
        Socket servidor     = new Socket("localhost", 1234);
        Conexao chat        = new Conexao(servidor);
        
        chat.start();
        chat.join();
    }
}

class Conexao extends Thread
{
    private Socket maquina;
    private Transmissor fala;
    private Transmissor escuta;

    public Conexao(Socket maquina)
        throws IOException
    {
        this.escuta	= new Transmissor(new Scanner(maquina.getInputStream()), System.out);
        this.fala	= new Transmissor(new Scanner(System.in), new PrintStream(maquina.getOutputStream()));
    }

    public void run()
    {
        try
        {
            this.fala.start();
            this.escuta.start();
            this.fala.join();
            this.escuta.join();
        }
        catch(Exception e) {}
    }
}

class Transmissor extends Thread {
    private Scanner in;
    private PrintStream out;

    public Transmissor(Scanner in, PrintStream out)
        throws IOException
    {
	    this.in	=	in;
	    this.out	=	out;
    }

    public void run()
    {
        while (this.in.hasNextLine())
            this.out.println(this.in.nextLine());

        this.in.close();
    }
}

