# Sistema Básico de Controle de Drone Tello

Este projeto consiste em um sistema de controle para o drone **Tello DJI**, desenvolvido em **Java**, utilizando conceitos de **Programação Orientada a Objetos (POO)**. O objetivo é gerenciar as funcionalidades e movimentos do drone de maneira organizada, garantindo modularidade, manutenção facilitada e a possibilidade de expansão futura do sistema.

## Funcionalidades Principais

- **Conexão com o drone Tello:** O sistema se conecta à rede Wi-Fi do drone para enviar comandos e monitorar o status.
- **Controle de movimentos:** Através de comandos do terminal, o usuário pode controlar os movimentos do drone, como decolagem, pouso, rotação, etc.
- **Monitoramento de status:** O sistema permite verificar o status atual do drone, como nível de bateria, altura, velocidade, entre outros.
- **Organização modular:** O código foi dividido em classes que representam diferentes responsabilidades, facilitando a leitura e manutenção.

## Estrutura do Projeto

O sistema é dividido em várias classes para garantir uma arquitetura modular e bem organizada:

- `Main.java`: Ponto de entrada da aplicação, onde a conexão com o drone é iniciada e o terminal de controle é apresentado ao usuário.
- `Drone.java`: Classe que representa o drone e seus atributos principais, como status e comandos.
- `DroneController.java`: Controla a lógica principal de interação com o drone, enviando comandos e recebendo feedback.
- `DroneMovement.java`: Responsável pelos movimentos do drone, como decolagem, pouso, rotações, etc.
- `DroneMoves.java`: Enumeração dos movimentos possíveis do drone.
- `DroneStatus.java`: Gerencia o status atual do drone, como nível de bateria, altitude e outras informações.
- `ComandoRede.java`: Responsável por gerenciar a comunicação entre o sistema e a rede do drone, enviando comandos e recebendo respostas da rede.

## Requisitos

- **Java 11+**
- **IntelliJ IDEA** (ou qualquer IDE de preferência para desenvolvimento Java)
- **Rede Wi-Fi do drone Tello** (O computador precisa estar conectado à rede Wi-Fi do drone)

## Como Executar

1. Conecte-se à rede Wi-Fi do drone Tello.
2. Compile e execute o projeto em sua IDE preferida ou no terminal.
3. O sistema apresentará um terminal interativo para controlar o drone.

```bash
javac Main.java
java Main
