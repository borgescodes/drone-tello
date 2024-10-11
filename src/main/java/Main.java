public class Main {
    public static void main(String[] args) {
        // Criando (objeto) a instância do drone
        Drone drone = new Drone();  // Verifique se a classe TesteDrone está funcionando corretamente

        // Criando o controlador e iniciando o processo
        DroneController droneController = new DroneController(drone);

        // Inicia o controle do drone
        droneController.start();
    }
}