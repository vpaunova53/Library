#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <cstdlib>
#include <ctime>
#include <iomanip>

using namespace std;

// Sensor class: generates temperature
class Sensor {
public:
    double readTemperature() {
        return 15 + (rand() % 1500) / 100.0; // Random temp between 15.00 and 30.00
    }
};

// Thermostat class: stores target temp
class Thermostat {
    double targetTemp;

public:
    Thermostat() : targetTemp(22.0) {} // default

    void setTarget(double temp) {
        targetTemp = temp;
    }

    double getTarget() const {
        return targetTemp;
    }

    bool needsHeating(double currentTemp) const {
        return currentTemp < targetTemp;
    }

    bool needsCooling(double currentTemp) const {
        return currentTemp > targetTemp;
    }
};

// Room class: combines sensor + thermostat
class Room {
    string name;
    Sensor sensor;
    Thermostat thermostat;

public:
    Room(const string& roomName) : name(roomName) {}

    void update(ofstream& logFile) {
        double currentTemp = sensor.readTemperature();
        bool heat = thermostat.needsHeating(currentTemp);
        bool cool = thermostat.needsCooling(currentTemp);

        cout << fixed << setprecision(2);
        cout << name << " - Current Temp: " << currentTemp << "°C | Target: "
             << thermostat.getTarget() << "°C → ";

        if (heat) {
            cout << "Heating ON";
        } else if (cool) {
            cout << "Cooling ON";
        } else {
            cout << "Stable";
        }

        cout << endl;

        logFile << name << " | Temp: " << currentTemp << " | Target: " << thermostat.getTarget() << "\n";
    }

    void setTargetTemperature(double temp) {
        thermostat.setTarget(temp);
    }

    string getName() const {
        return name;
    }
};

// Utility function
void displayMenu() {
    cout << "\n=== Smart Home Temperature Control ===\n";
    cout << "1. Show Temperature Status\n";
    cout << "2. Set Target Temperature for a Room\n";
    cout << "3. Exit\n";
    cout << "Choose an option: ";
}

int main() {
    srand(static_cast<unsigned>(time(0)));

    vector<Room> rooms = { Room("Living Room"), Room("Bedroom"), Room("Kitchen") };
    ofstream logFile("temperature_log.txt", ios::app);

    int choice;
    do {
        displayMenu();
        cin >> choice;

        switch (choice) {
            case 1:
                for (auto& room : rooms) {
                    room.update(logFile);
                }
                break;

            case 2: {
                cout << "\nAvailable Rooms:\n";
                for (size_t i = 0; i < rooms.size(); ++i) {
                    cout << i + 1 << ". " << rooms[i].getName() << endl;
                }
                cout << "Select a room number: ";
                int roomIndex;
                cin >> roomIndex;

                if (roomIndex >= 1 && roomIndex <= rooms.size()) {
                    cout << "Enter new target temperature: ";
                    double temp;
                    cin >> temp;
                    rooms[roomIndex - 1].setTargetTemperature(temp);
                    cout << "Target updated.\n";
                } else {
                    cout << "Invalid room selection.\n";
                }
                break;
            }

            case 3:
                cout << "Exiting program. Log saved to 'temperature_log.txt'.\n";
                break;

            default:
                cout << "Invalid option. Please try again.\n";
        }

    } while (choice != 3);

    logFile.close();
    return 0;
}
