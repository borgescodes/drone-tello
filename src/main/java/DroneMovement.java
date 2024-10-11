public interface DroneMovement {
    String moveUp(int distance);
    String moveDown(int distance);
    String moveLeft(int distance);
    String moveRight(int distance);
    String moveForward(int distance);
    String moveBack(int distance);
    String rotate(int degrees);
    String flip();
    String stop();
}
