import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Runnable;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteHandler implements Runnable {
    String nombre;
    Socket socket;
    ArrayList<ClienteHandler> clientes;
    PrintWriter salida;

    public ClienteHandler(String nombre, Socket socket, ArrayList<ClienteHandler> clientes) {
        this.nombre = nombre;
        this.socket = socket;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            this.salida = new PrintWriter(socket.getOutputStream(), true);
            this.nombre = entrada.readLine();
            clientes.add(this);

            String mensaje = "Usuario " + this.nombre + " conectado";
            enviarATodos(mensaje);


            String mensajeRecibido;
            while ((mensajeRecibido = entrada.readLine()) != null) {
                if (mensajeRecibido.equalsIgnoreCase("Chao")) {
                    enviarATodos("El usuario " + this.nombre + "  se desconecto");
                    clientes.remove(this);
                    socket.close();
                } else {
                    enviarATodos(mensajeRecibido);
                }
            }

        } catch (IOException e) {
            System.out.println("Run: " + e);
        }

    }

    public void enviarATodos(String mensaje) {
        clientes.forEach(cliente -> {
            cliente.salida.println(mensaje);
        });
    }

}
