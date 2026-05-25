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
            System.out.println("Usuario " + this.nombre + " conectado");
            clientes.add(this);

            String mensaje = "Usuario " + this.nombre + " conectado";
            enviarATodos(mensaje);

            String mensajeRecibido;
            while ((mensajeRecibido = entrada.readLine()) != null) {
               String minuscula = mensajeRecibido.toLowerCase();
                if (minuscula.contains("chao")) {
                    enviarATodos("El usuario " + this.nombre + "  se desconecto");
                    clientes.remove(this);
                    socket.close();
                } else {
                    boolean esMensajePrivado = false;
                    for (ClienteHandler cliente : clientes) {
                        if (mensajeRecibido.startsWith(cliente.nombre)) {
                            String contenido = mensajeRecibido.substring(cliente.nombre.length()).trim();
                            cliente.salida.println(this.nombre + " --> " + contenido);
                            esMensajePrivado = true;
                            break;
                        }
                    }
                    if (!esMensajePrivado) {
                        enviarATodos(mensajeRecibido);
                    }
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