    public class DroneStatus {
        private final ComandoRede rede;
        private int batteryLevel;
        private int speed;
        private int temperature;
        private int flightTime;
        private int altitude;

        public DroneStatus(ComandoRede rede) {
            this.rede = rede;
            updateAllStatus();
        }

        public void updateAllStatus() {
            updateBatteryStatus();
            updateSpeed();
            updateTemperature();
            updateFlightTime();
            updateAltitude();
        }

        public void updateBatteryStatus() {
            String response = rede.enviarMensagem("battery?");
            try {
                batteryLevel = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                batteryLevel = -1;
            }
        }

        public void updateSpeed() {
            speed = getIntStatus("speed?");
        }

        public void updateTemperature() {
            temperature = getIntStatus("temph");
        }

        public void updateFlightTime() {
            flightTime = getIntStatus("time?");
        }

        public void updateAltitude() {
            altitude = getIntStatus("h");
        }

        private int getIntStatus(String command) {
            try {
                return Integer.parseInt(rede.enviarMensagem(command));
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        public int getBatteryLevel() {
            return batteryLevel;
        }

        public int getSpeed() {
            return speed;
        }

        public int getTemperature() {
            return temperature;
        }

        public int getFlightTime() {
            return flightTime;
        }

        public int getAltitude() {
            return altitude;
        }
    }
