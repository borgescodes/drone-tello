import java.util.*;
import java.util.function.Function;

public class DroneController {
    private final Drone drone;
    private final DroneMoves droneMoves;  // Nova instância para movimentos
    private final Scanner scanner;
    private final Map<String, Runnable> mainMenu;
    private final List<Runnable> flightPlan;

    public DroneController(Drone drone) {
        this.drone = drone;
        this.droneMoves = new DroneMoves(drone);
        this.scanner = new Scanner(System.in);
        this.flightPlan = new ArrayList<>();
        this.mainMenu = initializeMainMenu();
    }

    // Inicialização do menu principal
    private Map<String, Runnable> initializeMainMenu() {
        Map<String, Runnable> cmdMap = new HashMap<>();
        cmdMap.put("1", this::droneStatus);
        cmdMap.put("2", this::controlMenu);
        cmdMap.put("3", this::flightPlanMenu);
        cmdMap.put("4", this::aboutMenu);
        cmdMap.put("0", this::exitProgram);
        return cmdMap;
    }

    // controlador com o loop do menu principal
    public void start() {
        System.out.println("\n=== Bem-vindo ao Sistema de Controle do Drone ===");
        System.out.println(drone.enterCommandMode());
        while (true) displayMainMenu();
    }

    // menu principal
    private void displayMainMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Status do Drone");
        System.out.println("2. Controle Manual");
        System.out.println("3. Plano de Voo");
        System.out.println("4. Sobre");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        String input = scanner.nextLine();
        Runnable command = mainMenu.get(input);
        if (command != null) {
            command.run();
        } else {
            System.out.println("Comando inválido. Tente novamente.");
        }
    }

    // status do drone
    private void droneStatus() {
        DroneStatus status = drone.getDroneStatus();
        System.out.println("\n--- Status do Drone ---");
        System.out.println("Nível de bateria: " + status.getBatteryLevel() + "%");
        System.out.println("Altura: " + status.getAltitude() + " cm");
        System.out.println("Velocidade: " + status.getSpeed() + " cm/s");
        System.out.println("Tempo de Voo: " + status.getFlightTime() + " segundos");
        System.out.println("Temperatura: " + status.getTemperature() + " °C");
    }

    // detalhes do nosso projeto
    private void aboutMenu() {
        System.out.println("\n--- Sobre ---");
        System.out.println("Projeto de controle de drone Tello.");
        System.out.println("Autores: Andreza Soares, Pedro Borges.");
        System.out.println("Curso: TADS 2024.");
        System.out.println("Docente: Rafael Gomes.");
    }

    // controle manual do drone
    private void controlMenu() {
        while (true) {
            System.out.println("\n--- Controle Manual ---");
            System.out.println("1. Decolar (t)");
            System.out.println("2. Pousar (l)");
            System.out.println("3. Subir (up)");
            System.out.println("4. Descer (down)");
            System.out.println("5. Mover Esquerda (left)");
            System.out.println("6. Mover Direita (right)");
            System.out.println("7. Avançar (forward)");
            System.out.println("8. Recuar (back)");
            System.out.println("9. Girar (rotate)");
            System.out.println("10. Ajustar Velocidade (speed)");
            System.out.println("11. Parar Drone no Ar (stop)");
            System.out.println("12. Rasga Mortalha (flip)");
            System.out.println("!. EMERGÊNCIA (stop motors)");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1", "t" -> System.out.println(drone.takeOff());
                case "2", "l" -> System.out.println(drone.land());
                case "3", "up" -> handleDirectionalMovement("up");
                case "4", "down" -> handleDirectionalMovement("down");
                case "5", "left" -> handleDirectionalMovement("left");
                case "6", "right" -> handleDirectionalMovement("right");
                case "7", "forward" -> handleDirectionalMovement("forward");
                case "8", "back" -> handleDirectionalMovement("back");
                case "9" -> System.out.println(droneMoves.rotate(getFlightParameter("graus para girar")));
                case "10" -> {
                    int speed = getFlightParameter("velocidade (cm/s)");
                    System.out.println(drone.setSpeed(speed));
                }
                case "11" -> System.out.println(droneMoves.stop());
                case "12" -> System.out.println(droneMoves.flip());
                case "!" -> System.out.println(drone.emergency());
                case "0" -> {
                    return;
                }
                default -> System.out.println("Comando inválido.");
            }
        }
    }

    // Método para tratar movimentos direcionais usando `DroneMoves`
    private void handleDirectionalMovement(String direction) {
        int distance = getFlightParameter("distância para mover " + direction);
        switch (direction) {
            case "up" -> System.out.println(droneMoves.moveUp(distance));
            case "down" -> System.out.println(droneMoves.moveDown(distance));
            case "left" -> System.out.println(droneMoves.moveLeft(distance));
            case "right" -> System.out.println(droneMoves.moveRight(distance));
            case "forward" -> System.out.println(droneMoves.moveForward(distance));
            case "back" -> System.out.println(droneMoves.moveBack(distance));
            default -> System.out.println("Direção inválida.");
        }
    }

    // definir e executar planos de voo
    private void flightPlanMenu() {
        while (true) {
            System.out.println("\n--- Plano de Voo ---");
            System.out.println("1. Definir Plano de Voo");
            System.out.println("2. Executar Plano de Voo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> definirVoo();
                case "2" -> executarVoo();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Comando inválido.");
            }
        }
    }

    // definir o plano de voo
    private void definirVoo() {
        flightPlan.clear(); // Limpa o plano de voo anterior
        System.out.println("\n--- Definir Plano de Voo ---");

        int speed = getFlightParameter("velocidade (cm/s)");
        System.out.println(drone.setSpeed(speed));

        // Adiciona a decolagem como o primeiro comando
        flightPlan.add(() -> System.out.println(drone.takeOff()));

        while (true) {
            System.out.println("\nAdicionar comando ao plano de voo (up, down, left, right, forward, back, rotate, flip). Digite 'concluir' para finalizar:");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "up" -> addFlightCommand("altura para subir", droneMoves::moveUp);
                case "down" -> addFlightCommand("altura para descer", droneMoves::moveDown);
                case "left" -> addFlightCommand("distância para mover à esquerda", droneMoves::moveLeft);
                case "right" -> addFlightCommand("distância para mover à direita", droneMoves::moveRight);
                case "forward" -> addFlightCommand("distância para avançar", droneMoves::moveForward);
                case "back" -> addFlightCommand("distância para recuar", droneMoves::moveBack);
                case "rotate" -> addFlightCommand("graus para girar", droneMoves::rotate);
                case "flip" -> flightPlan.add(() -> System.out.println(droneMoves.flip()));
                case "concluir" -> {
                    // Adiciona pouso como último comando e exibe a mensagem
                    flightPlan.add(() -> System.out.println(drone.land()));
                    System.out.println("Plano de voo definido com sucesso!");
                    return;
                }
                default -> System.out.println("Comando inválido. Tente novamente.");
            }
        }
    }

    // Adiciona comandos de voo ao plano
    private void addFlightCommand(String parameterPrompt, Function<Integer, String> droneCommand) {
        int parameter = getFlightParameter(parameterPrompt);
        flightPlan.add(() -> System.out.println(droneCommand.apply(parameter)));
    }

    // para executar o plano de voo
    private void executarVoo() {
        System.out.println("\n--- Executar Plano de Voo ---");

        // Executa os comandos e exibe mensagens para takeoff e land
        for (int i = 0; i < flightPlan.size(); i++) {
            System.out.print("Executando comando " + (i + 1) + ": ");
            flightPlan.get(i).run();
        }

        System.out.println("Plano de voo executado com sucesso!");
    }

    // para obter parâmetros de voo do usuário
    private int getFlightParameter(String prompt) {
        System.out.print("Digite " + prompt + ": ");
        return Integer.parseInt(scanner.nextLine());
    }

    // sair do programa
    private void exitProgram() {
        System.out.println("Saindo do programa...");
        System.exit(0);
    }
}
