public class Drone {
    private final ComandoRede rede;
    private final DroneMoves droneMoves;
    private final DroneStatus droneStatus;

    public Drone() {
        this.rede = new ComandoRede("192.168.10.1", 8889);
        this.droneMoves = new DroneMoves(this);
        this.droneStatus = new DroneStatus(rede);
    }

    public String enterCommandMode() {
        boolean commandMode = rede.enviarMensagem("command").equals("ok");
        if (commandMode) {
            droneStatus.updateAllStatus();
            return "Modo de comando ativado.";
        }
        return "Falha ao entrar no modo de comando. Verifique a conexão com o Bixo Voador";
    }

    public DroneMoves getDroneMoves() {
        return droneMoves;
    }

    public DroneStatus getDroneStatus() {
        return droneStatus;
    }

    // Métodos de controle do drone
    public String takeOff() {
        return executeCommand("takeoff");
    }

    public String land() {
        return executeCommand("land");
    }

    public String setSpeed(int speed) {
        String response = executeCommand("speed " + speed);
        if (response.equals("ok")) {
            droneStatus.updateSpeed();
            return "Velocidade definida para: " + speed + " cm/s.";
        }
        return "Falha ao definir velocidade.";
    }

    public String emergency() {
        return executeCommand("emergency");
    }

    public String executeCommand(String command) {
        String responseMessage = rede.enviarMensagem(command);
        if (responseMessage.equals("ok")) {
            return "Comando " + command + " executado com sucesso.";
        } else {
            return "Erro ao executar comando " + command + ".";
        }
    }
}
