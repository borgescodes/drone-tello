public class DroneMoves implements DroneMovement {
    private final Drone drone;

    // Construtor que recebe uma instância do Drone
    public DroneMoves(Drone drone) {
        this.drone = drone;
    }

    // Implementações dos métodos da interface DroneMovement
    @Override
    public String moveUp(int distance) {
        return move("up", distance);
    }

    @Override
    public String moveDown(int distance) {
        return move("down", distance);
    }

    @Override
    public String moveLeft(int distance) {
        return move("left", distance);
    }

    @Override
    public String moveRight(int distance) {
        return move("right", distance);
    }

    @Override
    public String moveForward(int distance) {
        return move("forward", distance);
    }

    @Override
    public String moveBack(int distance) {
        return move("back", distance);
    }

    @Override
    public String flip() {
        return drone.executeCommand("flip b"); // primeiro
        //return drone.executeCommand("flip f");
    }

    @Override
    public String stop() {
        return drone.executeCommand("stop");
    }

    @Override
    public String rotate(int degrees) {
        String response = drone.executeCommand("cw " + degrees);
        return response.equals("ok") ? "Girando " + degrees + " graus..." : "Falha ao girar. Resposta do drone: " + response;
    }

    private String move(String direction, int value) {
        String response = drone.executeCommand(direction + " " + value);
        return response.contains("sucesso") ? "Movendo " + direction + " " + value + " cm..." : "Falha ao mover " + direction + ". Resposta do drone: " + response;
    }
}
