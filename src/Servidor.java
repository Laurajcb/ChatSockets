import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Servidor {
    public static void main(String[] args) {
        try {
            int puerto = 59420;
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado y contestando ok");

            ArrayList<ClienteHandler> clientes = new ArrayList<>();
            while (true) {
                Socket clienteSocket = servidor.accept();

                ClienteHandler handler = new ClienteHandler("", clienteSocket, clientes);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

}
