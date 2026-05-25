import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 59420);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            Scanner input = new Scanner(System.in);
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Escriba su nombre: ");
            String nombre = input.nextLine();

            salida.println(nombre);


            Thread hiloRecibir = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println(mensaje);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            });

            Thread hiloEntregar = new Thread(() -> {
                while (true) {
                    String mensaje = input.nextLine();
                    salida.println(mensaje);
                }
            });

            hiloRecibir.start();
            hiloEntregar.start();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
