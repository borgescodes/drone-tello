import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComandoRede {

    private  String ip;
    private  int porta;

    public ComandoRede(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }


    private DatagramSocket ds = null;

    public String enviarMensagem(String mensagem) {
        //System.out.print(mensagem);

        InetAddress endereco = null;
        try {
            endereco = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ComandoRede.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ds == null) {
            try {
                ds = new DatagramSocket(porta);
//                ds.setSoTimeout(tempoEspera * 1000);
            } catch (SocketException ex) {
                Logger.getLogger(ComandoRede.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ds.connect(endereco, porta);
        byte buf[] = null;
        buf = mensagem.getBytes();
        DatagramPacket pacoteEnviado
                = new DatagramPacket(buf, buf.length, endereco, porta);
        try {
            ds.send(pacoteEnviado);
        } catch (IOException ex) {
            Logger.getLogger(ComandoRede.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] receiveData = new byte[1024];
        final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            ds.receive(receivePacket);
        } catch (IOException ex) {
            Logger.getLogger(ComandoRede.class.getName()).log(Level.SEVERE, "Não foi recebida Resposta. Verifique a conexão com o Drone.", ex);
        }
        String resposta = processarResposta(receiveData, receivePacket);
        try {
            ds.setReuseAddress(false);
        } catch (SocketException ex) {
            Logger.getLogger(ComandoRede.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Resposta do Moskito: " + resposta); // RETORNOOOOOOOOOOOOOOOOOOOOOOOO
        ds.disconnect();
        return resposta;
    }

    public static String processarResposta(byte[] resposta, DatagramPacket pacoteRecebido) {
        resposta = Arrays.copyOf(resposta, pacoteRecebido.getLength());
        return new String(resposta, StandardCharsets.UTF_8).trim();
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}
